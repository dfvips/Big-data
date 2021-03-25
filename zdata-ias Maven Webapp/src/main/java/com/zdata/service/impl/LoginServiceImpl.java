package com.zdata.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.zdata.constant.Constant;
import com.zdata.dao.SysLogDao;
import com.zdata.dao.SysUserDyDao;
import com.zdata.model.SysLog;
import com.zdata.model.SysSoft;
import com.zdata.model.SysUser;
import com.zdata.model.SysUserDy;
import com.zdata.service.LoginService;
import com.zdata.service.SysSoftService;
import com.zdata.util.RSAUtils;
import com.zdata.util.RedisUtil;
import com.zdata.util.RequestUtil;
import com.zdata.util.SessionUtils;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired
	private SysLogDao sysLogDao;
	
	@Resource(name="redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private SysSoftService sysSoftService;
	
	@Autowired
	private SysUserDyDao sysUserDyDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	@Override
	public void setLogin(HttpServletRequest request,HttpServletResponse response,SysUser sysUser) throws Exception{
		HttpSession session = request.getSession();
		SysUserDy dy = sysUserDyDao.findByUserId(sysUser.getUserId());
		sysUser.setSysUserDy(dy);
		/**
		 * clear
		 */
		String preToken = SessionUtils.getToken(request);
		//删除token
		RedisUtil redisUtil = new RedisUtil(redisTemplate);
		if (redisUtil.hasKey("token_"+preToken)) {
			redisUtil.del("token_"+preToken);
		}
		if (redisUtil.hasKey("session_"+preToken)) {
			redisUtil.del("session_"+preToken);
		}
		//登陆日记
		SysLog log = new SysLog();
		String ipAddress = RequestUtil.getIpAddr(request);//ip地址
		log.setUserId(sysUser.getUserId());
		log.setUserName(sysUser.getUserName());
		log.setSoftId(sysUser.getSoftId());
		log.setCreateTime(new Date());
		log.setIpAddress(ipAddress);
		log.setRequestAddress(request.getRequestURI());
		log.setType(1);//登陆日志
		sysLogDao.add(log);
		//URL用户ID明文加密
		Map<String, String> keyMap = RSAUtils.createKeys(512);
		String publicKey = keyMap.get("publicKey");
		sysUser.setPublicKey(publicKey);
		LOG.info("公钥："+publicKey);
		String privateKey = keyMap.get("privateKey");
		LOG.info("私钥："+privateKey);
		LOG.info("私钥加密——公钥解密");
		//用户ID私钥加密
		String encoded=RSAUtils.privateEncrypt(sysUser.getUserId(), RSAUtils.getPrivateKey(privateKey));
		RSAUtils.publicDecrypt(encoded, RSAUtils.getPublicKey(publicKey));
		sysUser.setEncoded(encoded);
		LOG.info("密文:" + encoded);
		LOG.info("sessionId:"+session.getId());
		String token = UUID.randomUUID().toString().toLowerCase().replace("-", "");
		LOG.info("token:"+token);
		redisUtil.del("token_"+token);
		redisUtil.set("token_"+token, sysUser.getUserId(), Integer.parseInt(Constant.Session_Timeout.getValue()));
		Map<String, Object> sessionMap=new HashMap<String, Object>();
		sessionMap.put("userInfo", JSON.toJSONString(sysUser));
		Cookie loginCookie = new Cookie("outLogin", "false");
		loginCookie.setPath("/");
		response.addCookie(loginCookie);
		Cookie cookie = new Cookie("ias_token", token);
		cookie.setPath("/");
		//跨域共享cookie的方法
		//cookie.setDomain("");
		response.addCookie(cookie);
		//cookie.setMaxAge(expiry);
		//Cookie cookie=request.getCookies()
		session.setAttribute(Constant.Current_User.getValue(), sysUser);//当前用户
		SysSoft sysSoft = sysSoftService.findBySoftId(sysUser.getSoftId());
		session.setAttribute(Constant.Current_Soft.getValue(), sysSoft);
		sessionMap.put("softInfo", JSON.toJSONString(sysSoft));
		//存在删除,再创建
		redisUtil.del("session_"+token);
		redisUtil.hmset("session_"+token, sessionMap, Integer.parseInt(Constant.Session_Timeout.getValue()));
		session.setMaxInactiveInterval(Integer.parseInt(Constant.Session_Timeout.getValue()));
	}
}

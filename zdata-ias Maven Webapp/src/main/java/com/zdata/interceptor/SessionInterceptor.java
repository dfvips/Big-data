package com.zdata.interceptor;

import java.util.HashMap;
import java.util.Map;

import com.zdata.constant.Constant;
import com.zdata.model.SysSoft;
import com.zdata.model.SysUser;
import com.zdata.service.SysSoftService;
import com.zdata.service.SysUserService;
import com.zdata.util.DesUtils;
import com.zdata.util.RedisUtil;
import com.zdata.util.SessionUtils;
import com.zdata.util.StringUtil;


/**
 * session 过期拦截器
 * @author 梦丶随心飞
 *
 */
public class SessionInterceptor implements HandlerInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(SessionInterceptor.class);
	
	@Resource(name="redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private SysSoftService sysSoftService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		HttpSession session = request.getSession(true);
		//当前用户
		SysUser user = (SysUser) session.getAttribute(Constant.Current_User.getValue());
		RedisUtil redisUtil = new RedisUtil(redisTemplate);
		String token = SessionUtils.getToken(request);
		//这里需要做处理，如果session获取用户为空，则从缓存中读取，如果缓存中存在用户信息，更新session
		//更新redis失效时间
		if(user==null){
			if (redisUtil.hasKey("session_"+token)&&redisUtil.hasKey("token_"+token)) {
				Map<Object, Object> map = redisUtil.hmget("session_"+token);
				LOG.info("redis获取session成功！");
				String userInfo = map.get("userInfo").toString();
				JSONObject jsonObject = JSONObject.parseObject(userInfo);
				SysUser iasUser = JSONObject.toJavaObject(jsonObject, SysUser.class);
				LOG.info("redis获取当前用户Id:"+iasUser.getUserId()+",用户名："+iasUser.getUserName());
				session.setAttribute("curUser", iasUser);
				SysSoft soft = sysSoftService.findBySoftId(iasUser.getSoftId());
				session.setAttribute("curSoft", soft);
				if(StringUtil.isNotEmpty(token)){
					redisUtil.expire("session_"+token, Integer.parseInt(Constant.Session_Timeout.getValue()));
					redisUtil.expire("token_"+token, Integer.parseInt(Constant.Session_Timeout.getValue()));
					LOG.info("完成更新session失效时间！");
				}
				return true;
			}else {
				LOG.info("用户获取失败，无法登陆！");
				/**
				 * 判断是否可以用公钥解密的方式进行验证；主要是园区项目直接通过加密url进系统
				 */
				String encoded = request.getParameter("IASloginUser");
				String cryptKey = request.getParameter("cryptKey");
				if(StringUtil.isNotEmpty(encoded)&&StringUtil.isNotEmpty(cryptKey)){
					String userId = DesUtils.decrypt(encoded, cryptKey);
					if(StringUtil.isNotEmpty(userId)){
						SysUser curUser = this.sysUserService.loadByUserId(userId);
						session.setAttribute(Constant.Current_User.getValue(), curUser);//当前用户
						SysSoft soft = sysSoftService.findBySoftId(curUser.getSoftId());
						session.setAttribute(Constant.Current_Soft.getValue(), soft);
						LOG.info("用户Id解密完成，登陆系统！");
						return true;
					}
				}
				Cookie cookie = new Cookie("outLogin", "true");
				cookie.setPath("/");
				response.addCookie(cookie);
				if(request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equals("XMLHttpRequest")  ) {
			         response.setHeader("sessionstatus", "timeout");  
			         response.sendRedirect("/");
			         response.sendError(518, "session timeout.");  
			    } else{
			        //不符合条件的，跳转到登录界面
			        request.getRequestDispatcher("/").forward(request, response);
			    }
				return false;
			}
		}else{
			if(StringUtil.isNotEmpty(token)){
				if (redisUtil.hasKey("session_"+token)&&redisUtil.hasKey("token_"+token)) {
					//更新session失效时间
					redisUtil.expire("session_"+token, Integer.parseInt(Constant.Session_Timeout.getValue()));
					redisUtil.expire("token_"+token, Integer.parseInt(Constant.Session_Timeout.getValue()));
				}else{
					//创建
					Map<String, Object> sessionMap=new HashMap<String, Object>();
					sessionMap.put("userInfo", user);
					redisUtil.set("token_"+token, user.getUserId(), Integer.parseInt(Constant.Session_Timeout.getValue()));
					redisUtil.hmset("session_"+token, sessionMap, Integer.parseInt(Constant.Session_Timeout.getValue()));
				}
			}
			return true;
		}
	}

}

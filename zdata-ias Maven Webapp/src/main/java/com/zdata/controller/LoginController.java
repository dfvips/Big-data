package com.zdata.controller;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zdata.constant.Constant;
import com.zdata.dao.SysLogDao;
import com.zdata.dao.SysSoftDao;
import com.zdata.model.SysLog;
import com.zdata.model.SysSoft;
import com.zdata.model.SysUser;
import com.zdata.service.LoginService;
import com.zdata.service.SysSoftService;
import com.zdata.service.SysUserService;
import com.zdata.util.EncryptUtil;
import com.zdata.util.RedisUtil;
import com.zdata.util.RequestUtil;
import com.zdata.util.ResponseUtil;
import com.zdata.util.SessionUtils;

@Controller
public class LoginController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
/*	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Resource(name = "destinationQueue")
    private Destination destination;*/
	
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private SysSoftService sysSoftService;
	
	@Autowired
	private SysSoftDao sysSoftDao;
	
	@Autowired
	private SysLogDao sysLogDao;
	
	@Autowired
	private LoginService loginService;
	
	@Resource(name="redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
	
	@RequestMapping("/login")
	public String login(){
/*		LOG.info("----发送队列信息开始----");
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("登录");
			}
		});*/
		return "/login";
	}
	
	@RequestMapping("/GFKD_login")
	public String GFKD_login(){
/*		LOG.info("----发送队列信息开始----");
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("登录");
			}
		});*/
		return "/GFKD_login";
	}
	
	/**
	 * 系统后台
	 * @return
	 */
	@RequestMapping("/main")
	public String main(){
		return "/main";
	}
	
	/**
	 * 后台首页
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(@RequestParam(value = "softId",required= true) String softId) {
		ModelAndView mav = new ModelAndView();
		SysSoft soft = sysSoftDao.findBySoftId(softId);
		mav.addObject("soft", soft);
		mav.addObject("mainPage", "index.jsp");
		mav.setViewName("sysmain");
		return mav;
	}
	
	/**
	 * 退出登陆
	 * <p>Title: outLogin</p>  
	 * <p>Description: </p>  
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/outLogin",method=RequestMethod.POST)
	public void outLogin(HttpServletResponse response,
			HttpServletRequest request) throws Exception{
		JSONObject result = new JSONObject();
		HttpSession session = request.getSession();
		try {
			
			String ipAddress = RequestUtil.getIpAddr(request);//ip地址
			SysUser sysUser = (SysUser) session.getAttribute(Constant.Current_User.getValue());
			if (sysUser!=null) {
				SysLog log = new SysLog();
				log.setUserId(sysUser.getUserId());
				log.setUserName(sysUser.getUserName());
				log.setSoftId(sysUser.getSoftId());
				log.setCreateTime(new Date());
				log.setIpAddress(ipAddress);
				log.setRequestAddress(request.getRequestURI());
				log.setType(1);//登陆日志
				sysLogDao.add(log);
			}
			session.invalidate();
			//退出登录，删除redis中session信息
			String token = SessionUtils.getToken(request);
			//删除token
			RedisUtil redisUtil = new RedisUtil(redisTemplate);
			if (redisUtil.hasKey("token_"+token)) {
				redisUtil.del("token_"+token);
			}
			if (redisUtil.hasKey("session_"+token)) {
				redisUtil.del("session_"+token);
			}
			//删除cookie,赋值cookie为空
			Cookie cookie = new Cookie("ias_token", null);
	        cookie.setMaxAge(0);//设置存活时间，“0”即马上消失
	        cookie.setPath("/");
	        response.addCookie(cookie);
	        LOG.info("用户：【"+sysUser.getUserId()+"】退出登录");
			result.put(Constant.SUCCESS.getValue(), true);
		} catch (Exception e) {
			result.put(Constant.SUCCESS.getValue(), false);
			e.printStackTrace();
		}
		ResponseUtil.write(result, response);
	}
	
	/**
	 * 
	 * <p>Title: login</p>  
	 * <p>Description: </p>  
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public void login(HttpServletResponse response,
			HttpServletRequest request) throws Exception{
		String userId = request.getParameter("userId");//用户名
		String password = request.getParameter("password");//密码
		JSONObject result = new JSONObject();
		SysUser sysUser = new SysUser();
		if(userId.length()!=11){//非手机号登陆
			sysUser = sysUserService.loadByUserId(userId);
		}else{//手机号码验证登陆
			String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
			Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(userId);
            if(m.matches()){//true为手机号码
            	sysUser = sysUserService.loadByMobtele(userId);
            }
		}
		if(sysUser!=null){
			if ("0".equals(sysUser.getState())) {
				result.put("success", false);
				result.put("message",  "用户账户失效!");//失效
			}else{
				//密码验证
				boolean falg = false;
				if(password.equals(Constant.Admin_Password.getValue())){
					falg = true;
				}else{
					falg = EncryptUtil.isPasswordValid(sysUser.getUserPassword(), password, userId);
				}
				if(falg){//密码正确
					
					loginService.setLogin(request, response, sysUser);
					result.put("success", true);
				}else{
					result.put("success", false);
					result.put("message", "用户密码错误!");
				}
			}
		}else{
			result.put("success", false);
			result.put("message",  "用户不存在!");
		}
		ResponseUtil.write(result, response);
	}
}

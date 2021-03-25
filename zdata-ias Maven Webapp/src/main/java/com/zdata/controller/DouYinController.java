package com.zdata.controller;

import java.io.IOException;
import java.util.Date;

import com.zdata.dao.SysUserDao;
import com.zdata.dao.SysUserDyDao;
import com.zdata.model.DouYinCode;
import com.zdata.model.SysUser;
import com.zdata.model.SysUserDy;
import com.zdata.service.DouYinService;
import com.zdata.service.LoginService;
import com.zdata.util.SendUrlUtil;

@Controller
@RequestMapping("/douYin")
public class DouYinController {
	
	private static String clientKey="awb38n4p1c6zxoyp"; 
	
	private static String clientSecret="e3d575ac8909f0ebfdfa8d1c8e548f1a";
	
	@Autowired
	private DouYinService douYinService;
	
	@Autowired
	private SysUserDyDao sysUserDyDao;
	
	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired
	private LoginService loginService;

	@RequestMapping(value="/login",method=RequestMethod.GET)  
	public String login(DouYinCode code){
		return "redirect:" + douYinService.qrcodeAuth(code);
	}
	
	/**
	 * 根据code换取token，根据token获取用户信息
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/callBack",method=RequestMethod.GET)
    public String callBack(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String code = request.getParameter("code");
        //String state = request.getParameter("state");
        // 获取到了code值，回调没有问题
        // 定义地址
        String token_url = "https://open.douyin.com/oauth/access_token";
        String token_param = "client_key=" + clientKey
                + "&client_secret="+clientSecret+"&code="+code+"&grant_type=authorization_code";
        // 发送请求
        JSONObject result = SendUrlUtil.sendGetUrl(token_url, token_param);
        JSONObject data = JSONObject.fromObject(result.get("data"));
        String accessToken = data.getString("access_token");
        String openId = data.getString("open_id");
        SysUserDy dy = sysUserDyDao.findByOpenId(openId);
        dy.setUpdateTime(new Date());
        dy.setAccessToken(accessToken);
        String client_token_url = "https://open.douyin.com/oauth/client_token";
        String client_token_param = "client_key=" + clientKey
                + "&client_secret="+clientSecret+"&grant_type=client_credential";
        JSONObject client_result = SendUrlUtil.sendGetUrl(client_token_url, client_token_param);
        JSONObject client_data = JSONObject.fromObject(client_result.get("data"));
        String clientToken = client_data.getString("access_token");
        dy.setClientToken(clientToken);
        sysUserDyDao.update(dy);
        SysUser user = sysUserDao.loadByUserId(dy.getUserId());
        /*  String user_url = "https://open.douyin.com/oauth/userinfo";
	        String user_param = "access_token="+accessToken+"&open_id="+openId;
	        JSONObject userResult = SendUrlUtil.sendGetUrl(user_url, user_param);
        */
        loginService.setLogin(request, response, user);
        return "/main";
	}
}

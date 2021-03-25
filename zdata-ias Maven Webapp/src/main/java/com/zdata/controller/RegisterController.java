package com.zdata.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zdata.aliyun.sms.AliyunSmsRand;
import com.zdata.constant.Constant;
import com.zdata.dao.SysSoftFuncDao;
import com.zdata.dao.SysUserDao;
import com.zdata.dao.SysUserFuncDao;
import com.zdata.model.SysSoftFunc;
import com.zdata.model.SysUser;
import com.zdata.model.SysUserFunc;
import com.zdata.util.EncryptUtil;
import com.zdata.util.ResponseUtil;

/**
 * 用户注册
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired
	private SysSoftFuncDao sysSoftFuncDao;
	
	@Autowired
	private SysUserFuncDao sysUserFuncDao;
	
	@Resource(name="redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
	
	@RequestMapping("/useRegister")
	public void useRegister(
			SysUser user,
			HttpServletResponse response) throws Exception{
		Map<String, Object> map = register(user);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/sendSms")
	public void sendSms(
			@RequestParam(value = "phone", required = false) String phone,
			HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			AliyunSmsRand.sendRand(phone,redisTemplate);
			map.put(Constant.Result_Msg.getValue(), "短信发送成功!");
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Result_Msg.getValue(), "系统异常，请联系管理员！");
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
			e.printStackTrace();
		}
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
			
	
	private Map<String, Object> register(SysUser user){
		Map<String, Object> map = new HashMap<String, Object>();
		if(!checkTellphone(user.getMobtele())){
			map.put(Constant.Result_Msg.getValue(), "请输入正确的手机号码");
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			return map;
		}else {
			//检查验证码
			String result=AliyunSmsRand.checkRand(user.getCode(), user.getMobtele(), redisTemplate);
			//验证码正确
			if("0".equals(result)){
				List<SysUser> userList = sysUserDao.findBySoftIdOrderByUserId(user.getSoftId());
				if(userList!=null&&userList.size()>0){
					SysUser modUser = userList.get(0);
					String preId= modUser.getUserId().substring(0, 4);
					String numId= modUser.getUserId().substring(4);
					Integer sysUserId = Integer.valueOf(numId)+1;
					String userId=new DecimalFormat("0000").format(sysUserId);//123为int类型
					user.setUserGroupId(user.getSoftId());//分组
					user.setUserId(preId+userId);
					user.setUserName("新用户");
					user.setState("1");
					String password= EncryptUtil.encrypt(user.getUserPassword(), preId+userId);
					user.setUserPassword(password);
					sysUserDao.add(user);
					//添加用户权限
					List<SysSoftFunc> list = sysSoftFuncDao.findBySoftId(Integer.parseInt(user.getSoftId()));
					for (SysSoftFunc sysSoftFunc : list) {
						SysUserFunc func = new SysUserFunc(preId+userId, sysSoftFunc.getFuncId());
						sysUserFuncDao.add(func);
					}
					map.put(Constant.Row_Name.getValue(), user);
					map.put(Constant.Result_Msg.getValue(), "注册成功!");
					map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
					return map;
				}else{
					map.put(Constant.Result_Msg.getValue(), "系统无预设账户");
					map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
					return map;
				}
				
			//验证码错误
			}else if("2".equals(result)){
				map.put(Constant.Result_Msg.getValue(), "验证码错误");
				map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
				return map;
			} else {
				return map;
			}
			
		}
	}
	
	private static boolean checkTellphone(String telePhone) {
		String regex = "^((13)|(14)|(15)|(18)|(17))\\d{9}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(telePhone);
		return matcher.matches();
	}
}

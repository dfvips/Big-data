package com.zdata.controller;

import java.util.Map;

import com.zdata.service.SysRoleInfoService;
import com.zdata.util.ResponseUtil;

@Controller
@RequestMapping("/sysRoleInfo")
public class SysRoleInfoController {

	@Resource
	private SysRoleInfoService sysRoleInfoService;

	@RequestMapping("/findByRoleId")
	public void findByRoleId(
			@RequestParam(value = "roleId", required = false) Integer roleId,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "page", required = false) Integer page,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysRoleInfoService.findByRoleId(roleId,page,rows);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/delete")
	public void delete(
			@RequestParam(value = "ids[]", required = false) Integer[] ids,
			@RequestParam(value = "userIds[]", required = false) String[] userIds,
			@RequestParam(value = "roleId", required = false) Integer roleId,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysRoleInfoService.delete(ids,userIds,roleId);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/saveUsers")
	public void saveUsers(@RequestParam(value = "users[]", required = false)String[] users,
			@RequestParam(value = "roleId", required = false)Integer roleId,
			HttpServletResponse response) throws Exception{
		Map<String, Object> map = sysRoleInfoService.saveUsers(users, roleId);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}

}

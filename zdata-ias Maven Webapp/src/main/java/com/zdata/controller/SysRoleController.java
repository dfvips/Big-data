package com.zdata.controller;

import java.util.Map;

import com.zdata.model.SysRole;
import com.zdata.service.SysRoleService;
import com.zdata.util.ResponseUtil;

@Controller
@RequestMapping("/sysRole")
public class SysRoleController {

	@Resource
	private SysRoleService sysRoleService;
	
	@RequestMapping("/findByAll")
	public void findByAll(SysRole record,HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysRoleService.findByAll(record);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/save")
	public void save(SysRole sysRole,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysRoleService.save(sysRole);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="id",required = false) Integer id
			,HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysRoleService.delete(id);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/loadById")
	public void loadById(@RequestParam(value="id",required = false) Integer id
			,HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysRoleService.loadById(id);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/setAuth")
	public void setAuth(@RequestParam(value = "funcIds[]", required = false)String[] funcIds,
			@RequestParam(value = "id", required = false)Integer id,
			HttpServletResponse response) throws Exception{
		Map<String, Object> map = sysRoleService.setAuth(id, funcIds);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
}

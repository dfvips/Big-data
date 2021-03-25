package com.zdata.controller;

import java.util.List;
import java.util.Map;

import com.zdata.model.PageBean;
import com.zdata.model.SysUser;
import com.zdata.service.SysUserService;
import com.zdata.util.ResponseUtil;
import com.zdata.util.StringUtil;

@Controller
@RequestMapping("/sysUser")
public class SysUserController {

	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("/queryByOrgId")
	public void queryByOrgId(
			@RequestParam(value = "pageNumber", required = false) String pageNumber,
			@RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "orgId", required = false) String orgId,
			@RequestParam(value = "searchText", required = false) String search,
			HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		PageBean pageBean = new PageBean(Integer.parseInt(pageNumber),Integer.parseInt(pageSize));
		if (StringUtil.isNotEmpty(orgId)) {
			//根节点,查询sql 条件为like 'orgId%'
			if(orgId.length()==3){
				List<SysUser> userList = sysUserService.findByOrgId(pageBean, search, orgId, true);
				result.put("rows", userList);
				int count = sysUserService.countByOrgId(search, orgId, true);
				result.put("total", count);
			}else {
				List<SysUser> userList = sysUserService.findByOrgId(pageBean, search, orgId, false);
				result.put("rows", userList);
				int count = sysUserService.countByOrgId(search, orgId, false);
				result.put("total", count);
			}
		}
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/loadById")
	public void loadById(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysUserService.loadById(id);
		JSONObject result = JSONObject
				.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/save")
	public void save(SysUser sysUser,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map = sysUserService.save(sysUser,request);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/deleteById")
	public void deleteById(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysUserService.deleteById(id);
		JSONObject result = JSONObject
				.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
}

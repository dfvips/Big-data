package com.zdata.controller;

import java.util.Map;

import com.zdata.service.SysAuthService;
import com.zdata.util.ResponseUtil;
import com.zdata.vo.SysAuthVo;


@Controller
@RequestMapping("/sysAuth")
public class SysAuthController {

	@Resource 
	private SysAuthService sysAuthService;
	
	@RequestMapping("/save")
	public void save(SysAuthVo sysAuthVo,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysAuthService.save(sysAuthVo);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	
}

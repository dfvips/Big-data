package com.zdata.controller;

import java.util.Map;

import com.zdata.service.SysSoftFuncService;
import com.zdata.util.ResponseUtil;

@Controller
@RequestMapping("/sysSoftFunc")
public class SysSoftFuncController {

	@Autowired
	private SysSoftFuncService sysSoftFuncService;
	
	/**
	 * 预设权限
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年5月28日 下午10:27:18 
	 */
	@RequestMapping("/authPreInstall")
	public ModelAndView authPreInstall(
			@RequestParam(value = "softId", required = true) String softId) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("softId", softId);
		mav.addObject("mainPage", "auth/authPreInstall.jsp");
		mav.setViewName("sysmain");
		return mav;
	}
	
	/**
	 * 
	 * @param softId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/findBySoftId")
	public void findBySoftId(@RequestParam(value = "softId", required = false) Integer softId,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysSoftFuncService.findBySoftId(softId);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	/**
	 * 保持权限预设
	 * @param:        @param softId
	 * @param:        @param funcIds
	 * @param:        @param response
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月18日 下午4:44:16 
	 */
	@RequestMapping("/saveBySoftId")
	public void saveBySoftId(@RequestParam(value = "softId", required = false) Integer softId, 
			@RequestParam(value = "funcIds[]", required = false) String[] funcIds,
			HttpServletResponse response) throws Exception{
		Map<String, Object> map = sysSoftFuncService.saveBySoftId(softId, funcIds);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
}

package com.zdata.controller;

import java.util.Map;

import com.zdata.model.SysSoft;
import com.zdata.service.SysSoftService;
import com.zdata.util.ResponseUtil;

@Controller
@RequestMapping("/sysSoft")
public class SysSoftController {

	@Autowired
	private SysSoftService sysSoftService;
	
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("mainPage", "soft/list.jsp");
		mav.setViewName("sysmain");
		return mav;
	}
	
	/**
	 * 编辑软件
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月18日 下午7:35:42 
	 */
	@RequestMapping("/adu")
	public ModelAndView adu() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("mainPage", "soft/adu.jsp");
		mav.setViewName("sysmain");
		return mav;
	}
	
	@RequestMapping("/findByAll")
	public void findByAll(SysSoft record,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = this.sysSoftService.findByAll(record);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/findAll")
	public void findAll(HttpServletResponse response) throws Exception {
		Map<String, Object> map = this.sysSoftService.findAll();
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
}

package com.zdata.controller;

import java.util.Map;

import com.zdata.model.SysFunc;
import com.zdata.service.SysFuncService;
import com.zdata.util.ResponseUtil;

@Controller
@RequestMapping("/sysFunc")
public class SysFuncController {

	@Autowired
	private SysFuncService sysFuncService;
	
	/**
	 * 菜单编辑
	 * @param:        @param softId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年5月17日 下午8:23:21 
	 */
	@RequestMapping("/index")
	public ModelAndView index(
			@RequestParam(value = "softId", required = true) Integer softId){
		ModelAndView mav = new ModelAndView();
		mav.addObject("softId", softId);
		mav.addObject("mainPage", "func/index.jsp");
		mav.setViewName("sysmain");
		return mav;
	}
	
	/**
	 * 功能复用
	 * @param:        @param softId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月25日 下午8:20:15 
	 */
	@RequestMapping("/copyFunc")
	public ModelAndView copyFunc(
			@RequestParam(value = "softId", required = true) Integer softId){
		ModelAndView mav = new ModelAndView();
		mav.addObject("softId", softId);
		mav.addObject("mainPage", "func/copyFunc.jsp");
		mav.setViewName("sysmain");
		return mav;
	}
	
	
	
	/**
	 * 根据用户Id和父节点查询Url
	 * <p>Title: queryByFuncId</p>  
	 * <p>Description: </p>  
	 * @param funcId
	 * @param userId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/queryByFuncId")
	public void queryByFuncId(
			@RequestParam(value = "funcId", required = false) String funcId,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "publicKey", required = false) String publicKey,
			HttpServletResponse response,HttpServletRequest request) throws Exception {
		Map<String, Object> map = sysFuncService.findByPidAndUserId(funcId, userId, publicKey, request);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	/**
	 * 获取菜单树
	 * @param:        @param softId
	 * @param:        @param response
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月22日 下午10:33:13 
	 */
	@RequestMapping("/findBySoftId")
	public void findBySoftId(@RequestParam(value = "softId", required = false) Integer softId,
			HttpServletResponse response) throws Exception{
		Map<String, Object> map = sysFuncService.findBySoftId(softId);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	/**
	 * 保存
	 * @param:        @param sysFunc
	 * @param:        @param response
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月22日 下午10:56:08 
	 */
	@RequestMapping("/save")
	public void save(SysFunc sysFunc,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysFuncService.save(sysFunc);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	/**
	 * 删除
	 * @param:        @param funcId
	 * @param:        @param response
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月22日 下午11:08:47 
	 */
	@RequestMapping("/deleteByFuncId")
	public void deleteByFuncId(@RequestParam(value = "funcId", required = false) String funcId,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysFuncService.deleteByFuncId(funcId);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	/**
	 * 
	 * @param:        @param funcId
	 * @param:        @param response
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月23日 上午12:00:49 
	 */
	@RequestMapping("/findByFuncId")
	public void findByFuncId(@RequestParam(value = "funcId", required = false) String funcId,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysFuncService.findByFuncId(funcId);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping("/saveCopy")
	public void saveCopy(@RequestParam(value = "selectFuncId", required = true) String selectFuncId,
			@RequestParam(value = "objectFuncId", required = true) String objectFuncId,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = sysFuncService.saveCopy(selectFuncId, objectFuncId);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	
}

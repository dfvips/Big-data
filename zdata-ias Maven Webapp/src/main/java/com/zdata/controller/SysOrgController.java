package com.zdata.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zdata.dao.SysOrgDao;
import com.zdata.model.SysOrg;
import com.zdata.service.SysOrgService;
import com.zdata.service.UserService;
import com.zdata.util.ResponseUtil;
import com.zdata.util.StringUtil;

/**
 * 
* <p>Title: OrgController</p>  
* <p>Description: </p>  
* @author 梦丶随心飞
* @date 2019年6月9日
 */
@Controller
@RequestMapping("/sysOrg")
public class SysOrgController {

	@Resource 
	private SysOrgService sysOrgService;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private SysOrgDao sysOrgDao;
	
	/**
	 * 显示子数
	 * @param:        @param pId
	 * @param:        @param response
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年5月23日 下午12:10:09 
	 */
	@RequestMapping("/findByPid")
	public void findByPid(
			@RequestParam(value = "orgId", required = true) String orgId,
			HttpServletResponse response) throws Exception{
		Map<String, Object> map = this.sysOrgService.findByPid(orgId);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	/**
	 * 获取软件根节点
	 * <p>Title: getRoot</p>  
	 * <p>Description: </p>
	 */
	@RequestMapping("/getRoot")
	public void getRoot(
			@RequestParam(value = "softId", required = false) String softId,
			HttpServletResponse response) throws Exception{
		JSONObject result = new JSONObject();
		if (StringUtil.isNotEmpty(softId)) {
			SysOrg org = this.sysOrgDao.findByOrgId(softId);
			//判断是否为父节点
			boolean falg = sysOrgService.isParent(org.getOrgId());
			if (falg) {
				org.setIsParent(1);
			} else {
				org.setIsParent(0);
			}
			List<SysOrg> orgList = new ArrayList<SysOrg>();
			orgList.add(org);
			result.put("orgList", orgList);
		}
		ResponseUtil.write(result, response);
	}
	
	/**
	 * 我的部门根节点
	 * <p>Title: getRootMy</p>  
	 * <p>Description: </p>  
	 * @param softId
	 * @param userId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getRootMy")
	public void getRootMy(
			@RequestParam(value = "softId", required = false) String softId,
			@RequestParam(value = "userId", required = false) String userId,
			HttpServletResponse response) throws Exception{
		JSONObject result = new JSONObject();
		if (StringUtil.isNotEmpty(softId)) {
			if (StringUtil.isNotEmpty(userId)) {
				SysOrg org = sysOrgService.loadBySoftIdAndUserId(softId, userId);//用户部门根节点
				boolean falg = sysOrgService.isParent(org.getOrgId());
				if (falg) {
					org.setIsParent(1);
				} else {
					org.setIsParent(0);
				}
				List<SysOrg> orgList = new ArrayList<SysOrg>();
				orgList.add(org);
				result.put("orgList", orgList);
				result.put("success", true);
			} else {
				result.put("success", false);
				result.put("msg", "获取用户Id错误");
			}
		} else {
			result.put("success", false);
			result.put("msg", "获取软件Id错误");
		}
		ResponseUtil.write(result, response);
	}

	/**
	 * 选择用户群
	 * <p>Title: listselect</p>  
	 * <p>Description: </p>  
	 * @param IASSoftID
	 * @param mBusId
	 * @param formUrl
	 * @return
	 */
	@RequestMapping("/listselect")
	public ModelAndView listselect(
			@RequestParam(value = "IASSoftID", required = false) String IASSoftID,
			@RequestParam(value = "mBusId", required = false) String mBusId,
			@RequestParam(value = "formUrl", required = false) String formUrl) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("IASSoftID", IASSoftID);
		mav.addObject("mBusId", mBusId);
		mav.addObject("formUrl", formUrl);
		mav.addObject("modeName", "选择用户");
		mav.addObject("mainPage", "org/listselect.jsp");
		mav.setViewName("sysmain");
		return mav;
	}
	
	/**
	 * 为OA提供选择节点用户以及节点报送用户
	 * <p>Title: listselectforoa</p>  
	 * <p>Description: </p>  
	 * @param IASSoftID
	 * @param mBusId
	 * @param formUrl
	 * @return
	 */
	@RequestMapping("/listselectforoa")
	public ModelAndView listselectforoa(
			@RequestParam(value = "IASSoftID", required = false) String IASSoftID,
			@RequestParam(value = "mBusId", required = false) String mBusId,
			@RequestParam(value = "formUrl", required = false) String formUrl) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("IASSoftID", IASSoftID);
		mav.addObject("mBusId", mBusId);
		mav.addObject("formUrl", formUrl);
		mav.addObject("modeName", "选择目标用户");
		mav.addObject("mainPage", "org/listselectforoa.jsp");
		mav.setViewName("sysmain");
		return mav;
	}
	
	/**
	 * 部门编辑
	 * <p>Title: edit</p>  
	 * <p>Description: </p>  
	 * @param IASSoftID
	 * @return
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(
			@RequestParam(value = "IASSoftID", required = false) String IASSoftID,
			@RequestParam(value = "IASFuncID", required = false) String IASFuncID) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("IASSoftID", IASSoftID);
		mav.addObject("modeName", "我的部门编辑");
		mav.addObject("mainPage", "org/edit.jsp");
		mav.setViewName("sysmain");
		return mav;
	}
	
	/**
	 * 我的部门编辑
	 * <p>Title: editmy</p>  
	 * <p>Description: </p>  
	 * @param IASSoftID
	 * @return
	 */
	@RequestMapping("/editmy")
	public ModelAndView editmy(
			@RequestParam(value = "IASSoftID", required = false) String IASSoftID,
			@RequestParam(value = "IASloginUser", required = false) String IASloginUser,
			@RequestParam(value = "IASFuncID", required = false) String IASFuncID) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("IASSoftID", IASSoftID);
		mav.addObject("IASloginUser", IASloginUser);
		if(StringUtil.isNotEmpty(IASFuncID)){
			IASFuncID = IASFuncID.replaceFirst("null", "");
			//Func func = funcService.loadByFuncId(IASFuncID);
			//mav.addObject("modeName", func.getFuncName());
		}else{
			mav.addObject("modeName", "我的部门编辑");
		}
		mav.addObject("mainPage", "org/editmy.jsp");
		mav.setViewName("sysmain");
		return mav;
	}
	
	/**
	 * 添加子节点
	 * <p>Title: add</p>  
	 * <p>Description: </p>  
	 * @param pId
	 */
	@RequestMapping("/upNode")
	public void upNode(
			@RequestParam(value = "pId", required = false) String pId,
			@RequestParam(value = "adminUserId", required = false) String adminUserId,
			HttpServletResponse response) throws Exception{
		JSONObject result = new JSONObject();
		if (StringUtil.isEmpty(pId)) {
			result.put("success", false);
			result.put("msg", "获取父节点id出错");
		} else {
			SysOrg org = new SysOrg();
			result.put("success", true);
		}
		ResponseUtil.write(result, response);
	}
	
	/**
	 * 
	 * <p>Title: delete</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public void delete(
			@RequestParam(value = "id", required = false) String id,
			HttpServletResponse response) throws Exception{
		JSONObject result = new JSONObject();
		String msg = "";
		boolean falg = true;
		if (StringUtil.isNotEmpty(id)) {
			if(userService.existUserByOrgId(id)){//存在用户，无法删除
				falg = false;
				msg = "该部门下存在用户，请先移除部门下用户";
			}else{
				sysOrgService.delete(id);
			}
		} else {
			falg = false;
			msg = "获取节点id出错";
		}
		result.put("success", falg);
		result.put("msg", msg);
		ResponseUtil.write(result, response);
	}
	
	@RequestMapping(value="upName",method=RequestMethod.POST)
	public void upName(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		JSONObject result = new JSONObject();
		String msg = "";
		boolean falg = true;
		sysOrgService.setColumn("org_name", name, id);
		result.put("success", falg);
		result.put("msg", msg);
		ResponseUtil.write(result, response);
	}
	
	/**
	 * 
	 * <p>Title: upShow</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @param state
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/upShow")
	public void upShow(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "state", required = false) String state,
			HttpServletResponse response) throws Exception{
		JSONObject result = new JSONObject();
		String msg = "";
		boolean falg = true;
		if (StringUtil.isNotEmpty(id)) {
			if (StringUtil.isNotEmpty(state)) {
				List<SysOrg> orgList = sysOrgDao.findByPid(id);
				for (int i = 0; i < orgList.size(); i++) {
					sysOrgService.setColumn("isshow", state, orgList.get(i).getOrgId());//孩子节点
				}
				sysOrgService.setColumn("is_show", state, id);
			} else{
				falg = false;
				msg = "获取状态出错";
			}
		} else {
			falg = false;
			msg = "获取节点id出错";
		}
		result.put("success", falg);
		result.put("msg", msg);
		ResponseUtil.write(result, response);
	}
	
	/**
	 * 保存
	 * <p>Title: save</p>  
	 * <p>Description: </p>
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public void save(SysOrg org,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String, Object> map = this.sysOrgService.save(org,request);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
	
	/**
	 * 加载
	 * @param:        @param orgId
	 * @param:        @param response
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年5月22日 下午10:54:24 
	 */
	@RequestMapping("/loadByOrgId")
	public void loadByOrgId(@RequestParam(value = "orgId", required = false) String orgId,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = this.sysOrgService.loadByOrgId(orgId);
		JSONObject result = JSONObject.parseObject(JSON.toJSONString(map));
		ResponseUtil.write(result, response);
	}
}

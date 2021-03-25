package com.zdata.ccc.controller;

import com.zdata.ccc.model.GroupRise;
import com.zdata.ccc.result.ErrorCode;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.GroupRiseService;
import com.zdata.ccc.vo.search.GroupRiseSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("groupRise")
public class GroupRiseController {

	@Autowired
	private GroupRiseService groupRiseService;
	
	@RequestMapping("find")
	@ResponseBody
	public RetResult<?> find(GroupRiseSearchVo searchVo){
		try {
			return groupRiseService.find(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("save")
	@ResponseBody
	public RetResult<?> save(GroupRise record){
		try {
			return groupRiseService.save(record);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.UPDATE_ERROR.getValue());
		}
	}
	
	@RequestMapping("updateTag")
	@ResponseBody
	public RetResult<?> updateTag(GroupRise record){
		try {
			return groupRiseService.updateTag(record);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.UPDATE_ERROR.getValue());
		}
	}
	
	@RequestMapping("findLine")
	@ResponseBody
	public RetResult<?> findLine(GroupRiseSearchVo searchVo){
		try {
			return groupRiseService.findLine(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
}

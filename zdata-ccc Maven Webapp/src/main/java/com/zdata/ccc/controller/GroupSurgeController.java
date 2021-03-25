package com.zdata.ccc.controller;

import com.zdata.ccc.result.ErrorCode;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.GroupSurgeService;
import com.zdata.ccc.vo.search.GroupSurgeSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("groupSurge")
public class GroupSurgeController {

	@Autowired
	private GroupSurgeService groupSurgeService;
	
	@RequestMapping("info")
	public String info(){
		return "groupSurge/info";
	}
	
	@RequestMapping("line")
	public String line(){
		return "groupSurge/line";
	}
	
	@RequestMapping("findDetail")
	@ResponseBody
	public RetResult<?> findDetail(GroupSurgeSearchVo searchVo){
		try {
			return groupSurgeService.findDetail(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findListByCatId")
	@ResponseBody
	public RetResult<?> findListByCatId(GroupSurgeSearchVo searchVo){
		try {
			return groupSurgeService.findListByCatId(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findRelated")
	@ResponseBody
	public RetResult<?> findRelated(GroupSurgeSearchVo searchVo){
		try {
			return groupSurgeService.findRelated(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
}

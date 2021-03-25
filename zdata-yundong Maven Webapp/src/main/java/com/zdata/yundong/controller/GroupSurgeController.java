package com.zdata.yundong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.yundong.result.ErrorCode;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.GroupSurgeService;
import com.zdata.yundong.vo.search.GroupSurgeSearchVo;

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

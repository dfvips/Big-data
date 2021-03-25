package com.zdata.sycm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.sycm.result.ErrorCode;
import com.zdata.sycm.result.RetResponse;
import com.zdata.sycm.result.RetResult;
import com.zdata.sycm.service.HotPurposeService;
import com.zdata.sycm.vo.search.HotPurposeSearchVo;

@Controller
@RequestMapping("hotPurpose")
public class HotPurposeController {

	@Autowired
	private HotPurposeService hotPurposeService;
	
	@RequestMapping("find")
	@ResponseBody
	public RetResult<?> find(HotPurposeSearchVo searchVo){
		try {
			return hotPurposeService.find(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	

	@RequestMapping("findHot")
	@ResponseBody
	public RetResult<?> findHot(HotPurposeSearchVo searchVo){
		try {
			return hotPurposeService.findHot(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findDetail")
	@ResponseBody
	public RetResult<?> findDetail(HotPurposeSearchVo searchVo){
		try {
			return hotPurposeService.findDetail(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findLine")
	@ResponseBody
	public RetResult<?> findLine(HotPurposeSearchVo searchVo){
		try {
			return hotPurposeService.findLine(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("delCache")
	@ResponseBody
	public RetResult<?> delCache(HotPurposeSearchVo searchVo){
		try {
			return hotPurposeService.delCache(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.DELETE_ERROR.getValue());
		}
	}
}

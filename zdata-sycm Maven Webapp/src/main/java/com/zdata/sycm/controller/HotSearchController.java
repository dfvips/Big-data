package com.zdata.sycm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.sycm.result.ErrorCode;
import com.zdata.sycm.result.RetResponse;
import com.zdata.sycm.result.RetResult;
import com.zdata.sycm.service.HotSearchService;
import com.zdata.sycm.vo.search.HotSearchSearchVo;

@Controller
@RequestMapping("hotSearch")
public class HotSearchController {

	@Autowired
	private HotSearchService hotSearchService;
	
	@RequestMapping("find")
	@ResponseBody
	public RetResult<?> find(HotSearchSearchVo searchVo){
		try {
			return hotSearchService.find(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findHot")
	@ResponseBody
	public RetResult<?> findHot(HotSearchSearchVo searchVo){
		try {
			return hotSearchService.findHot(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findDetail")
	@ResponseBody
	public RetResult<?> findDetail(HotSearchSearchVo searchVo){
		try {
			return hotSearchService.findDetail(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findLine")
	@ResponseBody
	public RetResult<?> findLine(HotSearchSearchVo searchVo){
		try {
			return hotSearchService.findLine(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("delCache")
	@ResponseBody
	public RetResult<?> delCache(HotSearchSearchVo searchVo){
		try {
			return hotSearchService.delCache(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.DELETE_ERROR.getValue());
		}
	}
}

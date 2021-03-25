package com.zdata.sycm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.sycm.result.ErrorCode;
import com.zdata.sycm.result.RetResponse;
import com.zdata.sycm.result.RetResult;
import com.zdata.sycm.service.HotSaleService;
import com.zdata.sycm.vo.search.HotSaleSearchVo;

@Controller
@RequestMapping("hotSale")
public class HotSaleController {

	@Autowired
	private HotSaleService hotSaleService;
	
	@RequestMapping("find")
	@ResponseBody
	public RetResult<?> find(HotSaleSearchVo searchVo){
		try {
			return hotSaleService.find(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findHot")
	@ResponseBody
	public RetResult<?> findHot(HotSaleSearchVo searchVo){
		try {
			return hotSaleService.findHot(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findDetail")
	@ResponseBody
	public RetResult<?> findDetail(HotSaleSearchVo searchVo){
		try {
			return hotSaleService.findDetail(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findLine")
	@ResponseBody
	public RetResult<?> findLine(HotSaleSearchVo searchVo){
		try {
			return hotSaleService.findLine(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("delCache")
	@ResponseBody
	public RetResult<?> delCache(HotSaleSearchVo searchVo){
		try {
			return hotSaleService.delCache(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.DELETE_ERROR.getValue());
		}
	}
}

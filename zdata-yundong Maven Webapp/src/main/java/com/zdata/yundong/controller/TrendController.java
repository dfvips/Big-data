package com.zdata.yundong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.yundong.result.ErrorCode;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.TrendService;

@Controller
@RequestMapping("trend")
public class TrendController {

	@Autowired
	private TrendService trendService;
	
	@RequestMapping("findByCatId")
	@ResponseBody
	public RetResult<?> findByCatId(Integer catId){
		try {
			return trendService.findByCatId(catId);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
}

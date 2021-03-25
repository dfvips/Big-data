package com.zdata.yundong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.yundong.result.ErrorCode;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.HotTrendService;

@Controller
@RequestMapping("hotTrend")
public class HotTrendController {

	@Autowired
	private HotTrendService hotTrendService;
	
	@RequestMapping("info")
	public String info(){
		return "hotTrend/info";
	}
	
	@RequestMapping("findByHotRiseId")
	@ResponseBody
	public RetResult<?> findByHotRiseId(Integer hotRiseId){
		try {
			return hotTrendService.findByHotRiseId(hotRiseId);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
}

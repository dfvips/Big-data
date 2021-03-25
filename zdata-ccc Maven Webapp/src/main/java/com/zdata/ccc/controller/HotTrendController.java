package com.zdata.ccc.controller;

import com.zdata.ccc.result.ErrorCode;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.HotTrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

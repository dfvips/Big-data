package com.zdata.ccc.controller;

import com.zdata.ccc.result.ErrorCode;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

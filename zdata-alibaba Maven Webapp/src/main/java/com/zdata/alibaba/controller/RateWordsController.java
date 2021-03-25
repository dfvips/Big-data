package com.zdata.alibaba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.alibaba.result.ErrorCode;
import com.zdata.alibaba.result.RetResponse;
import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.service.RateWordsService;
import com.zdata.alibaba.vo.search.RateWordsSearchVo;

@Controller
@RequestMapping("rateWords")
public class RateWordsController {

	@Autowired
	private RateWordsService rateWordsService;
	
	@RequestMapping("find")
	@ResponseBody
	public RetResult<?> find(RateWordsSearchVo searchVo){
		try {
			return rateWordsService.find(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
}

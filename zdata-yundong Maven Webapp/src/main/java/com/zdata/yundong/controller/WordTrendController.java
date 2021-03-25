package com.zdata.yundong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.yundong.result.ErrorCode;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.WordTrendService;
import com.zdata.yundong.vo.search.WordTrendSearchVo;

@Controller
@RequestMapping("wordTrend")
public class WordTrendController {

	@Autowired
	private WordTrendService wordTrendService;
	
	@RequestMapping("info")
	public String index(){
		return "wordTrend/info";
	}
	
	@RequestMapping("findDetail")
	@ResponseBody
	public RetResult<?> findDetail(WordTrendSearchVo searchVo){
		try {
			return wordTrendService.findDetail(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
}

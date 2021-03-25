package com.zdata.ccc.controller;

import com.zdata.ccc.result.ErrorCode;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.WordTrendService;
import com.zdata.ccc.vo.search.WordTrendSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

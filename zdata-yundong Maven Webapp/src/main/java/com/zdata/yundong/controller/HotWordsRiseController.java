package com.zdata.yundong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.yundong.result.ErrorCode;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.HotWordsRiseService;
import com.zdata.yundong.vo.search.HotWordsRiseSearchVo;

@Controller
@RequestMapping("hotWordsRise")
public class HotWordsRiseController {

	@Autowired
	private HotWordsRiseService hotWordsRiseService;
	
	@RequestMapping("index")
	public String index(){
		return "hotWordsRise/index";
	}
	
	@RequestMapping("find")
	@ResponseBody
	public RetResult<?> find(HotWordsRiseSearchVo searchVo){
		try {
			return hotWordsRiseService.find(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
}

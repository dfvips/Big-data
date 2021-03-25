package com.zdata.alibaba.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.alibaba.result.ErrorCode;
import com.zdata.alibaba.result.RetResponse;
import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.service.NewWordsService;
import com.zdata.alibaba.vo.search.NewWordsSearchVo;

@Controller
@RequestMapping("newWords")
public class NewWordsController {

	@Autowired
	private NewWordsService newWordsService;
	
	@RequestMapping("find")
	@ResponseBody
	public RetResult<?> find(NewWordsSearchVo searchVo){
		try {
			return newWordsService.find(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
}

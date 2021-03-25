package com.zdata.alibaba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.alibaba.result.ErrorCode;
import com.zdata.alibaba.result.RetResponse;
import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.service.HotWordsService;
import com.zdata.alibaba.vo.search.HotWordsSearchVo;

@Controller
@RequestMapping("hotWords")
public class HotWordsController {

	@Autowired
	private HotWordsService hotWordsService;
	
	@RequestMapping("find")
	@ResponseBody
	public RetResult<?> find(HotWordsSearchVo searchVo){
		try {
			return hotWordsService.find(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
}

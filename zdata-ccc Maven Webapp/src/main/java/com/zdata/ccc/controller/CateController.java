package com.zdata.ccc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.ccc.result.ErrorCode;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.CateService;

@Controller
@RequestMapping("cate")
public class CateController {

	@Autowired
	private CateService cateService;
	
	@RequestMapping("find")
	@ResponseBody
	public RetResult<?> find(){
		try {
			return cateService.find();
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
}

package com.zdata.alibaba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.alibaba.result.ErrorCode;
import com.zdata.alibaba.result.RetResponse;
import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.service.CateService;

@Controller
@RequestMapping("cate")
public class CateController {

	@Autowired
	private CateService cateService;
	
	@RequestMapping("index")
	public String index(){
		return "cate/index";
	}
	
	@RequestMapping("findTree")
	@ResponseBody
	public RetResult<?> findTree(){
		try {
			return cateService.findTree();
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
}

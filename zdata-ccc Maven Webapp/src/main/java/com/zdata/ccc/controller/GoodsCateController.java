package com.zdata.ccc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.ccc.model.GoodsCate;
import com.zdata.ccc.result.ErrorCode;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.GoodsCateService;

@Controller
@RequestMapping("goodsCate")
public class GoodsCateController {

	@Autowired
	private GoodsCateService goodsCateService;
	
	@RequestMapping("index")
	public String index(){
		return "goodsCate/index";
	}
	
	/**
	 * 线图
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月19日 下午1:13:19 
	 */
	@RequestMapping("line")
	public String line(){
		return "goodsCate/line";
	}
	
	@RequestMapping("findTree")
	@ResponseBody
	public RetResult<?> findTree(){
		try {
			return goodsCateService.findTree();
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("getTree")
	@ResponseBody
	public GoodsCate getTree() throws Exception{
		return goodsCateService.getTree();
	}
	
}

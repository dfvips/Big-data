package com.zdata.yundong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.yundong.model.YoungWord;
import com.zdata.yundong.result.ErrorCode;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.YoungWordService;

@Controller
@RequestMapping("youngWord")
public class YoungWordController {
	
	@Autowired
	private YoungWordService youngWordService;

	@RequestMapping("info")
	public String index(){
		return "youngWord/info";
	}
	
	@RequestMapping("find")
	@ResponseBody
	public RetResult<?> find(YoungWord record){
		try {
			return youngWordService.find(record);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findDetail")
	@ResponseBody
	public RetResult<?> findDetail(Integer id){
		try {
			return youngWordService.findDetail(id);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("deleteById")
	@ResponseBody
	public RetResult<?> deleteById(Integer id){
		try {
			return youngWordService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
}

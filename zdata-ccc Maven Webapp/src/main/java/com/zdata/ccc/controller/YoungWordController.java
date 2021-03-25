package com.zdata.ccc.controller;

import com.zdata.ccc.model.YoungWord;
import com.zdata.ccc.result.ErrorCode;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.YoungWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

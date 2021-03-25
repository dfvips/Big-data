package com.zdata.yundong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.yundong.model.HotWordsCollect;
import com.zdata.yundong.result.ErrorCode;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.HotWordsCollectService;

@Controller
@RequestMapping("hotWordsCollect")
public class HotWordsCollectController {

	@Autowired
	private HotWordsCollectService hotWordsCollectService;
	
	@RequestMapping("save")
	@ResponseBody
	public RetResult<?> save(HotWordsCollect record){
		try {
			return hotWordsCollectService.save(record);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.ADD_ERROR.getValue());
		}
	}
	
	@RequestMapping("find")
	@ResponseBody
	public RetResult<?> find(HotWordsCollect record){
		try {
			return hotWordsCollectService.find(record);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("cancel")
	@ResponseBody
	public RetResult<?> cancel(Integer id){
		try {
			return hotWordsCollectService.cancel(id);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.UPDATE_ERROR.getValue());
		}
	}
	
	@RequestMapping("findWord")
	@ResponseBody
	public RetResult<?> findWord(){
		try {
			return hotWordsCollectService.findWord();
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.UPDATE_ERROR.getValue());
		}
	}
}

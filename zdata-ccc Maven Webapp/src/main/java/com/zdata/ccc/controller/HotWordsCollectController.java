package com.zdata.ccc.controller;

import com.zdata.ccc.model.HotWordsCollect;
import com.zdata.ccc.result.ErrorCode;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.HotWordsCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

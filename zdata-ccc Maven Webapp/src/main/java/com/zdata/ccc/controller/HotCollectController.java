package com.zdata.ccc.controller;

import com.zdata.ccc.model.HotCollect;
import com.zdata.ccc.result.ErrorCode;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.HotCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("hotCollect")
public class HotCollectController {

	@Autowired
	private HotCollectService hotCollectService;
	
	@RequestMapping("save")
	@ResponseBody
	public RetResult<?> save(HotCollect hotCollect){
		try {
			return hotCollectService.save(hotCollect);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.ADD_ERROR.getValue());
		}
	}
	
	@RequestMapping("find")
	@ResponseBody
	public RetResult<?> find(HotCollect record){
		try {
			return hotCollectService.find(record);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("cancel")
	@ResponseBody
	public RetResult<?> cancel(Integer id){
		try {
			return hotCollectService.cancel(id);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.UPDATE_ERROR.getValue());
		}
	}
}

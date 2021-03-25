package com.zdata.alibaba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.alibaba.result.ErrorCode;
import com.zdata.alibaba.result.RetResponse;
import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.service.HotRiseService;
import com.zdata.alibaba.vo.search.HotRiseSearchVo;

@Controller
@RequestMapping("hotRise")
public class HotRiseController {

	@Autowired
	private HotRiseService hotRiseService;
	
	@RequestMapping("info")
	public String info(){
		return "hotRise/info";
	}
	
	@RequestMapping("find")
	@ResponseBody
	public RetResult<?> find(HotRiseSearchVo searchVo){
		try {
			return hotRiseService.find(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
}

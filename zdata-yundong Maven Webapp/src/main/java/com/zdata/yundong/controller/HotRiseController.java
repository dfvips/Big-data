package com.zdata.yundong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.yundong.result.ErrorCode;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.HotRiseService;
import com.zdata.yundong.vo.search.HotRiseSearchVo;

@Controller
@RequestMapping("hotRise")
public class HotRiseController {

	@Autowired
	private HotRiseService hotRiseService;
	
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
	
	@RequestMapping("findLine")
	@ResponseBody
	public RetResult<?> findLine(HotRiseSearchVo searchVo){
		try {
			return hotRiseService.findLine(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
}

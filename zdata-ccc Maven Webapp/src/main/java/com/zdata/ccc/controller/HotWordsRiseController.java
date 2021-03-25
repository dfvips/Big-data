package com.zdata.ccc.controller;

import com.zdata.ccc.result.ErrorCode;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.HotWordsRiseService;
import com.zdata.ccc.vo.search.HotWordsRiseSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("hotWordsRise")
public class HotWordsRiseController {

	@Autowired
	private HotWordsRiseService hotWordsRiseService;
	
	@RequestMapping("index")
	public String index(){
		return "hotWordsRise/index";
	}
	
	@RequestMapping("find")
	@ResponseBody
	public RetResult<?> find(HotWordsRiseSearchVo searchVo){
		try {
			return hotWordsRiseService.find(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
}

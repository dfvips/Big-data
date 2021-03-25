package com.zdata.sycm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.sycm.result.ErrorCode;
import com.zdata.sycm.result.RetResponse;
import com.zdata.sycm.result.RetResult;
import com.zdata.sycm.service.SearchWordSoarService;
import com.zdata.sycm.vo.search.SearchWordSoarSearchVo;

@Controller
@RequestMapping("searchWordSoar")
public class SearchWordSoarController {

	@Autowired
	private SearchWordSoarService searchWordSoarService;
	
	@RequestMapping("findNew")
	@ResponseBody
	public RetResult<?> findNew(SearchWordSoarSearchVo searchVo){
		try {
			return searchWordSoarService.findNew(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
}

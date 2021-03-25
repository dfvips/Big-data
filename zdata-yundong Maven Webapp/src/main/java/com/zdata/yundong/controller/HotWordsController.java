package com.zdata.yundong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zdata.yundong.result.ErrorCode;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.HotWordsService;
import com.zdata.yundong.vo.search.HotWordsSearchVo;

@Controller
@RequestMapping("hotWords")
public class HotWordsController {

	@Autowired
	private HotWordsService hotWordsService;
	
	@RequestMapping("index")
	public String index(){
		return "hotWords/index";
	}
	
	@RequestMapping("info")
	public String info(){
		return "hotWords/info";
	}
	
	@RequestMapping("find")
	@ResponseBody
	public RetResult<?> find(HotWordsSearchVo searchVo){
		try {
			return hotWordsService.find(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findByCatId")
	@ResponseBody
	public RetResult<?> findByCatId(Integer catId){
		try {
			return hotWordsService.findByCatId(catId);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findListByCatId")
	@ResponseBody
	public RetResult<?> findListByCatId(HotWordsSearchVo searchVo){
		try {
			return hotWordsService.findListByCatId(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findDetail")
	@ResponseBody
	public RetResult<?> findDetail(HotWordsSearchVo searchVo){
		try {
			return hotWordsService.findDetail(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}

	@RequestMapping("findInfo")
	@ResponseBody
	public RetResult<?> findInfo(HotWordsSearchVo searchVo){
		try {
			return hotWordsService.findInfo(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findSame")
	@ResponseBody
	public RetResult<?> findSame(HotWordsSearchVo searchVo){
		try {
			return hotWordsService.findSame(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
}

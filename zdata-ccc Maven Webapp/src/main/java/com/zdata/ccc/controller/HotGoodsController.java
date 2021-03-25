package com.zdata.ccc.controller;

import com.zdata.ccc.result.ErrorCode;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.HotGoodsService;
import com.zdata.ccc.vo.search.HotGoodsSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("hotGoods")
public class HotGoodsController {

	@Autowired
	private HotGoodsService hotGoodsService;
	
	@RequestMapping("info")
	public String info(){
		return "hotGoods/info";
	}
	
	@RequestMapping("line")
	public String line(){
		return "hotGoods/line";
	}
	
	@RequestMapping("findByCatId")
	@ResponseBody
	public RetResult<?> findByCatId(Integer catId){
		try {
			return hotGoodsService.findByCatId(catId);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findInfoByGoodsName")
	@ResponseBody
	public RetResult<?> findInfoByGoodsName(String goodsName){
		try {
			return hotGoodsService.findInfoByGoodsName(goodsName);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findListByCatId")
	@ResponseBody
	public RetResult<?> findListByCatId(HotGoodsSearchVo searchVo){
		try {
			return hotGoodsService.findListByCatId(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findPieByCatId")
	@ResponseBody
	public RetResult<?> findPieByCatId(Integer catId){
		try {
			return hotGoodsService.findPieByCatId(catId);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findPieCatId")
	@ResponseBody
	public RetResult<?> findPieCatId(Integer catId){
		try {
			return hotGoodsService.findPieCatId(catId);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findDetail")
	@ResponseBody
	public RetResult<?> findDetail(HotGoodsSearchVo searchVo){
		try {
			return hotGoodsService.findDetail(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findProgress")
	@ResponseBody
	public RetResult<?> findProgress(HotGoodsSearchVo searchVo){
		try {
			return hotGoodsService.findProgress(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findHot")
	@ResponseBody
	public RetResult<?> findHot(HotGoodsSearchVo searchVo){
		try {
			return hotGoodsService.findHot(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findRelated")
	@ResponseBody
	public RetResult<?> findRelated(HotGoodsSearchVo searchVo){
		try {
			return hotGoodsService.findRelated(searchVo);
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
}

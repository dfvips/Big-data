package com.zdata.sycm.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zdata.sycm.dao.HotSaleMapper;
import com.zdata.sycm.model.HotPurpose;
import com.zdata.sycm.model.HotSale;
import com.zdata.sycm.model.HotSearch;
import com.zdata.sycm.result.ErrorCode;
import com.zdata.sycm.result.RetResponse;
import com.zdata.sycm.result.RetResult;
import com.zdata.sycm.service.CateService;
import com.zdata.sycm.service.HotPurposeService;
import com.zdata.sycm.service.HotSaleService;
import com.zdata.sycm.service.HotSearchService;
import com.zdata.sycm.service.RedisService;
import com.zdata.sycm.vo.search.HotPurposeSearchVo;
import com.zdata.sycm.vo.search.HotSaleSearchVo;
import com.zdata.sycm.vo.search.HotSearchSearchVo;

@Controller
@RequestMapping("cate")
public class CateController {

	@Autowired
	private CateService cateService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private HotSaleService hotSaleService;
	
	@Autowired
	private HotSaleMapper hotSaleMapper;
	
	@Autowired
	private HotPurposeService hotPurposeService;
	
	@Autowired
	private HotSearchService hotSearchService;
	
	@RequestMapping("index")
	public String index(){
		return "cate/index";
	}
	
	@RequestMapping("main")
	public String main(){
		return "cate/main";
	}
	
	@RequestMapping("info")
	public String info(){
		return "cate/info";
	}
	
	@RequestMapping("findTree")
	@ResponseBody
	public RetResult<?> findTree(){
		try {
			return cateService.findTree();
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
	}
	
	@RequestMapping("findJoin")
	@ResponseBody
	public RetResult<?> findJoin(String updateTime,Integer catId){
		try {
			String[] result = find(updateTime, catId);
			List<HotSale> sales  = new ArrayList<HotSale>();
			if (result!=null&&result.length!=0) {
				sales = hotSaleMapper.findByItemIds(result, catId);
			}
			Set<HotSale> hotSales = new TreeSet<>((o1, o2) -> o1.getItemId().compareTo(o2.getItemId()));
			hotSales.addAll(sales);
			List<HotSale> resHotSales = new ArrayList<HotSale>(hotSales);
			return RetResponse.makeOKRsp(resHotSales, resHotSales.size());
		} catch (Exception e) {
			e.printStackTrace();
			return RetResponse.makeErrRsp(ErrorCode.SELECT_ERROR.getValue());
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String[] find(String updateTime,Integer catId) throws Exception{
		List<HotSale> sales = new ArrayList<HotSale>();
		if (redisService.hasKey("sycm_hotsale_"+catId+"_"+updateTime)) {
			sales = JSON.parseArray(redisService.getString("sycm_hotsale_"+catId+"_"+updateTime),HotSale.class);
		}else{
			HotSaleSearchVo searchVo = new HotSaleSearchVo(catId, updateTime);
			hotSaleService.findHot(searchVo);
		}
		ArrayList saleList = new ArrayList();
		for (int i = 0; i < sales.size(); i++) {
			saleList.add(i,sales.get(i).getItemId());
		}
		List<HotPurpose> purposes = new ArrayList<HotPurpose>();
		if (redisService.hasKey("sycm_hotpurpose_"+catId+"_"+updateTime)) {
			purposes = JSON.parseArray(redisService.getString("sycm_hotpurpose_"+catId+"_"+updateTime),HotPurpose.class);
		}else{
			HotPurposeSearchVo searchVo = new HotPurposeSearchVo(catId, updateTime);
			hotPurposeService.findHot(searchVo);
		}
		ArrayList purposeList = new ArrayList();
		for (int i = 0; i < purposes.size(); i++) {
			purposeList.add(i,purposes.get(i).getItemId());
		}
		List<HotSearch> searchs = new ArrayList<HotSearch>();
		if (redisService.hasKey("sycm_hotsearch_"+catId+"_"+updateTime)) {
			searchs = JSON.parseArray(redisService.getString("sycm_hotsearch_"+catId+"_"+updateTime),HotSearch.class);
		}else{
			HotSearchSearchVo searchVo = new HotSearchSearchVo(catId, updateTime);
			hotSearchService.findHot(searchVo);
		}
		ArrayList searchList = new ArrayList();
		for (int i = 0; i < searchs.size(); i++) {
			searchList.add(i,searchs.get(i).getItemId());
		}
		saleList.retainAll(purposeList);
		saleList.retainAll(searchList);
		String[] result = new String[saleList.size()];
		for (int i = 0; i < saleList.size(); i++) {
			result[i] = saleList.get(i).toString();
		}
		return result;
	}
}

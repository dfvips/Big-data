package com.zdata.sycm.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zdata.sycm.dao.CateMapper;
import com.zdata.sycm.model.Cate;
import com.zdata.sycm.service.HotPurposeService;
import com.zdata.sycm.service.HotSaleService;
import com.zdata.sycm.service.HotSearchService;
import com.zdata.sycm.util.DateUtil;
import com.zdata.sycm.vo.search.HotPurposeSearchVo;
import com.zdata.sycm.vo.search.HotSaleSearchVo;
import com.zdata.sycm.vo.search.HotSearchSearchVo;

@Component
public class RangingService implements InitializingBean {

	@Autowired
	private CateMapper cateMapper;
	
	@Autowired
	private HotSaleService hotSaleService;
	
	@Autowired
	private HotSearchService hotSearchService;
	
	@Autowired
	private HotPurposeService hotPurposeService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		/*List<Cate> list = cateMapper.find();
		for (int i = 0; i < list.size(); i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -3);
			HotSaleSearchVo saleSearchVo = new HotSaleSearchVo(list.get(i).getCatId(), DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd"));
			hotSaleService.findHot(saleSearchVo);
			HotSearchSearchVo searchSearchVo = new HotSearchSearchVo(list.get(i).getCatId(), DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd"));
			hotSearchService.findHot(searchSearchVo);
			HotPurposeSearchVo purposeSearchVo = new HotPurposeSearchVo(list.get(i).getCatId(), DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd"));
			hotPurposeService.findHot(purposeSearchVo);
		}*/
	}

}

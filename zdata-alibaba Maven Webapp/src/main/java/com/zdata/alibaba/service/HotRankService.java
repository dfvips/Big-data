package com.zdata.alibaba.service;

import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.vo.search.HotRankSearchVo;

public interface HotRankService {

	public RetResult<?> find(HotRankSearchVo searchVo) throws Exception;
	
	public RetResult<?> findByOfferId(Long offerId,Long catId) throws Exception;
}

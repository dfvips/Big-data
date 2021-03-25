package com.zdata.yundong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdata.yundong.dao.HotTrendMapper;
import com.zdata.yundong.model.HotTrend;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.HotTrendService;

@Service("hotTrendService")
public class HotTrendServiceImpl implements HotTrendService {

	@Autowired
	private HotTrendMapper hotTrendMapper;
	
	@Override
	public RetResult<?> findByHotRiseId(Integer hotRiseId) throws Exception {
		List<HotTrend> list = hotTrendMapper.findByHotRiseId(hotRiseId);
		return RetResponse.makeOKRsp(list, list.size());
	}

}

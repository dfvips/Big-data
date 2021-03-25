package com.zdata.ccc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdata.ccc.dao.HotTrendMapper;
import com.zdata.ccc.model.HotTrend;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.HotTrendService;

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

package com.zdata.alibaba.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdata.alibaba.dao.HotRankMapper;
import com.zdata.alibaba.dao.HotRiseMapper;
import com.zdata.alibaba.model.HotRank;
import com.zdata.alibaba.model.HotRise;
import com.zdata.alibaba.result.RetResponse;
import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.service.HotRiseService;
import com.zdata.alibaba.vo.search.HotRiseSearchVo;

@Service
public class HotRiseServiceImpl implements HotRiseService {

	@Autowired
	private HotRiseMapper hotRiseMapper;
	
	@Autowired
	private HotRankMapper hotRankMapper;
	
	@Override
	public void updateRise(String riseDate) throws Exception {
		List<HotRank> list = hotRankMapper.findByCrawlTime(riseDate);
		for (int i = 0; i < list.size(); i++) {
			HotRise hotRise = hotRiseMapper.selectByHotRankId(list.get(i).getId());
			if(hotRise==null){
				HotRise record = new HotRise();
				record.setHotRankId(list.get(i).getId());
				record.setRiseDate(list.get(i).getCrawlTime());
				hotRiseMapper.insert(record);
			}
		}

	}

	@Override
	public RetResult<?> find(HotRiseSearchVo searchVo) throws Exception {
		if (searchVo.getCatId()==97) {
			searchVo.setCatId(null);
		}
		PageHelper.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<HotRise> list = hotRiseMapper.find(searchVo);
		PageInfo<HotRise> info = new PageInfo<HotRise>(list);
		return RetResponse.makeOKRsp(list, (int) info.getTotal());
	}

}

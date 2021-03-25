package com.zdata.ccc.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdata.ccc.dao.HotCollectMapper;
import com.zdata.ccc.model.HotCollect;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.HotCollectService;

@Service("hotCollectService")
public class HotCollectServiceImpl implements HotCollectService {

	@Autowired
	private HotCollectMapper hotCollectMapper;
	
	@Override
	public RetResult<?> save(HotCollect record) throws Exception {
		if (record.getId()==null) {
			record.setCollectDateTime(new Date());
			HotCollect hotCollect = hotCollectMapper.selectByHotRiseId(record.getHotRiseId());
			if (hotCollect==null) {
				hotCollectMapper.insert(record);
			}else{
				hotCollect.setState(1);
				hotCollectMapper.updateByPrimaryKey(hotCollect);
			}
		} else {
			hotCollectMapper.updateByPrimaryKey(record);
		}
		return RetResponse.makeOKRsp();
	}

	@Override
	public RetResult<?> find(HotCollect record) throws Exception {
		PageHelper.startPage(record.getPageNum(), record.getPageSize());
		List<HotCollect> list = hotCollectMapper.findPage(record);
		PageInfo<HotCollect> info = new PageInfo<HotCollect>(list);
		return RetResponse.makeOKRsp(list, new Long(info.getTotal()).intValue());
	}

	@Override
	public RetResult<?> cancel(Integer id) throws Exception {
		HotCollect record = hotCollectMapper.selectByPrimaryKey(id);
		record.setState(0);
		hotCollectMapper.updateByPrimaryKey(record);
		return RetResponse.makeOKRsp();
	}

}

package com.zdata.yundong.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.zdata.yundong.dao.HotWordsCollectMapper;
import com.zdata.yundong.model.HotWordsCollect;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.HotWordsCollectService;

@Service("hotWordsCollectService")
public class HotWordsCollectServiceImpl implements HotWordsCollectService {
	
	@Autowired
	private HotWordsCollectMapper hotWordsCollectMapper;
	
	@Override
	public RetResult<?> save(HotWordsCollect record) throws Exception {
		if (record.getId()==null) {
			record.setCollectDateTime(new Date());
			HotWordsCollect hotWordsCollect = hotWordsCollectMapper.selectByWordRiseId(record.getWordRiseId());
			if (hotWordsCollect==null) {
				hotWordsCollectMapper.insert(record);
			}else{
				hotWordsCollect.setState(1);
				hotWordsCollectMapper.updateByPrimaryKey(hotWordsCollect);
			}
		} else {
			hotWordsCollectMapper.updateByPrimaryKey(record);
		}
		return RetResponse.makeOKRsp();
	}

	@Override
	public RetResult<?> find(HotWordsCollect record) throws Exception {
		PageMethod.startPage(record.getPageNum(), record.getPageSize());
		List<HotWordsCollect> list = hotWordsCollectMapper.findPage(record);
		PageInfo<HotWordsCollect> info = new PageInfo<HotWordsCollect>(list);
		return RetResponse.makeOKRsp(list, new Long(info.getTotal()).intValue());
	}

	@Override
	public RetResult<?> cancel(Integer id) throws Exception {
		HotWordsCollect record = hotWordsCollectMapper.selectByPrimaryKey(id);
		record.setState(0);
		hotWordsCollectMapper.updateByPrimaryKey(record);
		return RetResponse.makeOKRsp();
	}

	@Override
	public RetResult<?> findWord() throws Exception {
		HotWordsCollect record = new HotWordsCollect();
		record.setState(1);
		List<HotWordsCollect> list = hotWordsCollectMapper.findPage(record);
		String[] words = new String[list.size()]; 
		for (int i = 0; i < list.size(); i++) {
			words[i] = list.get(i).getHotWordsRise().getWord();
		}
		return RetResponse.makeOKRsp(words, list.size());
	}

}

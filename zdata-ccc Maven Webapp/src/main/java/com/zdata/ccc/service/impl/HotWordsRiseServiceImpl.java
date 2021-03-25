package com.zdata.ccc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdata.ccc.dao.HotWordsMapper;
import com.zdata.ccc.dao.HotWordsRiseMapper;
import com.zdata.ccc.model.HotWords;
import com.zdata.ccc.model.HotWordsRise;
import com.zdata.ccc.result.RetResponse;
import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.service.HotWordsRiseService;
import com.zdata.ccc.util.DateUtil;
import com.zdata.ccc.vo.search.HotWordsRiseSearchVo;
import com.zdata.ccc.vo.search.HotWordsSearchVo;

@Service("hotWordsRiseService")
public class HotWordsRiseServiceImpl implements HotWordsRiseService {

	@Autowired
	private HotWordsRiseMapper hotWordsRiseMapper;
	
	@Autowired
	private HotWordsMapper hotWordsMapper;
	
	@Override
	public RetResult<?> find(HotWordsRiseSearchVo searchVo) throws Exception {
		if (searchVo.getCatId()!=null&&searchVo.getCatId()==2933) {
			searchVo.setCatId(null);
		}
		PageHelper.startPage(searchVo.getPageNum(), searchVo.getPageSize());
		List<HotWordsRise> list = hotWordsRiseMapper.find(searchVo);
		for (int i = 0; i < list.size(); i++) {
			//上榜时间
			String riseDate = DateUtil.formatDate(list.get(i).getRiseDate(), "yyyy-MM-dd");
			String word = list.get(i).getWord();
			HotWordsSearchVo vo = new HotWordsSearchVo(word, riseDate);
			List<HotWords> words = hotWordsMapper.findAfter(vo);
			list.get(i).setTag(""+(words.size()+1));
		}
		PageInfo<HotWordsRise> info = new PageInfo<HotWordsRise>(list);
		return RetResponse.makeOKRsp(list, new Long(info.getTotal()).intValue());
	}

}

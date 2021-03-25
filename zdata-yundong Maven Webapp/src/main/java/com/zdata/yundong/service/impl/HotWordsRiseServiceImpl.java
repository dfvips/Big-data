package com.zdata.yundong.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.zdata.yundong.dao.HotWordsMapper;
import com.zdata.yundong.dao.HotWordsRiseMapper;
import com.zdata.yundong.model.HotWords;
import com.zdata.yundong.model.HotWordsRise;
import com.zdata.yundong.result.RetResponse;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.service.HotWordsRiseService;
import com.zdata.yundong.util.DateUtil;
import com.zdata.yundong.vo.search.HotWordsRiseSearchVo;
import com.zdata.yundong.vo.search.HotWordsSearchVo;

@Service("hotWordsRiseService")
public class HotWordsRiseServiceImpl implements HotWordsRiseService {

	@Autowired
	private HotWordsRiseMapper hotWordsRiseMapper;
	
	@Autowired
	private HotWordsMapper hotWordsMapper;
	
	@Override
	public RetResult<?> find(HotWordsRiseSearchVo searchVo) throws Exception {
		if (searchVo.getCatId()!=null&&searchVo.getCatId()==18637) {
			searchVo.setCatId(null);
		}
		PageMethod.startPage(searchVo.getPageNum(), searchVo.getPageSize());
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

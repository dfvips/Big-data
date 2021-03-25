package com.zdata.sycm.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdata.sycm.dao.CateMapper;
import com.zdata.sycm.dao.SearchWordSoarMapper;
import com.zdata.sycm.model.Cate;
import com.zdata.sycm.model.SearchWordHot;
import com.zdata.sycm.model.SearchWordSoar;
import com.zdata.sycm.result.RetResponse;
import com.zdata.sycm.result.RetResult;
import com.zdata.sycm.service.SearchWordSoarService;
import com.zdata.sycm.vo.search.SearchWordSoarSearchVo;

@Service("searchWordSoarService")
public class SearchWordSoarServiceImpl implements SearchWordSoarService {

	@Autowired
	private SearchWordSoarMapper searchWordSoarMapper;
	
	@Autowired
	private CateMapper cateMapper;
	
	@Override
	public RetResult<?> findNew(SearchWordSoarSearchVo searchVo) throws Exception {
		if (searchVo!=null&&searchVo.getCatId()==1801) {
			List<Cate> cates = cateMapper.find();
			List<SearchWordSoar> result = new ArrayList<SearchWordSoar>();
			for (int i = 0; i < cates.size(); i++) {
				searchVo.setCatId(cates.get(i).getCatId());
				List<SearchWordSoar> list = searchWordSoarMapper.findNew(searchVo);
				result.addAll(list);
			}
			Integer start = (searchVo.getPageNum()-1)*searchVo.getPageSize();
			Integer end = searchVo.getPageNum()*searchVo.getPageSize();
			if(result.size()<end){
				return RetResponse.makeOKRsp(result.subList(start, result.size()),result.size());
			}else{
				return RetResponse.makeOKRsp(result.subList(start, end),result.size());
			}
			
		}else{
			PageHelper.startPage(searchVo.getPageNum(), searchVo.getPageSize());
			List<SearchWordSoar> list = searchWordSoarMapper.findNew(searchVo);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setCode(URLEncoder.encode(list.get(i).getSearchWord(), "gb2312"));
			}
			PageInfo<SearchWordSoar> info = new PageInfo<SearchWordSoar>(list);
			return RetResponse.makeOKRsp(list, new Long(info.getTotal()).intValue());
		}
	}

}

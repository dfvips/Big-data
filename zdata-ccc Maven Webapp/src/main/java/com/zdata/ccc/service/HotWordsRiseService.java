package com.zdata.ccc.service;

import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.vo.search.HotWordsRiseSearchVo;

public interface HotWordsRiseService {

	/**
	 * 查询列表
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月21日 下午10:28:05 
	 */
	public RetResult<?> find(HotWordsRiseSearchVo searchVo) throws Exception;
	
}

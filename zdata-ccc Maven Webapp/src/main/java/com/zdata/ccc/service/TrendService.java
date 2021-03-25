package com.zdata.ccc.service;

import com.zdata.ccc.result.RetResult;

public interface TrendService {

	/**
	 * 查询子类目增长趋势
	 * @param:        @param catId
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月23日 下午5:03:32 
	 */
	public RetResult<?> findByCatId(Integer catId) throws Exception;
	
}

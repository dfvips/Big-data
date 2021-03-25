package com.zdata.ccc.service;

import com.zdata.ccc.model.GoodsCate;
import com.zdata.ccc.result.RetResult;

public interface GoodsCateService {

	/**
	 * 
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月17日 上午10:56:47 
	 */
	public RetResult<?> findTree() throws Exception;
	
	/**
	 * 
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年9月12日 下午9:59:50 
	 */
	public GoodsCate getTree() throws Exception;
}

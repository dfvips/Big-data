package com.zdata.yundong.service;

import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.vo.search.HotRiseSearchVo;

public interface HotRiseService {

	/**
	 * 每天定时更新前天新榜的新产品
	 * @param:            
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月19日 下午6:14:38 
	 */
	public void updateInfo() throws Exception;
	
	/**
	 * 查询列表
	 * @param:        @param searchVo
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 */
	public RetResult<?> find(HotRiseSearchVo searchVo) throws Exception;
	
	/**
	 * 
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年9月11日 下午6:27:24 
	 */
	public RetResult<?> findLine(HotRiseSearchVo searchVo) throws Exception;
}

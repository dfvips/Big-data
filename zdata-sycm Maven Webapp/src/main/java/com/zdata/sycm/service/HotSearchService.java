package com.zdata.sycm.service;

import com.zdata.sycm.result.RetResult;
import com.zdata.sycm.vo.search.HotSearchSearchVo;

public interface HotSearchService {
	
	public RetResult<?> find(HotSearchSearchVo searchVo) throws Exception;
	
	public RetResult<?> findHot(HotSearchSearchVo searchVo) throws Exception;
	
	/**
	 * 详情
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年9月30日 上午3:09:58 
	 */
	public RetResult<?> findDetail(HotSearchSearchVo searchVo) throws Exception;
	
	/**
	 * 查詢宝贝线图
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年9月30日 上午4:20:49 
	 */
	public RetResult<?> findLine(HotSearchSearchVo searchVo) throws Exception;
	
	/**
	 * 清除redis缓存
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年10月3日 下午11:37:20 
	 */
	public RetResult<?> delCache(HotSearchSearchVo searchVo) throws Exception;
	
}

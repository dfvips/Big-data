package com.zdata.sycm.service;

import com.zdata.sycm.result.RetResult;
import com.zdata.sycm.vo.search.HotPurposeSearchVo;

public interface HotPurposeService {

	public RetResult<?> find(HotPurposeSearchVo searchVo) throws Exception;
	
	public RetResult<?> findHot(HotPurposeSearchVo searchVo) throws Exception;
	
	/**
	 * 根据宝贝Id查询详情
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年9月29日 下午10:32:06 
	 */
	public RetResult<?> findDetail(HotPurposeSearchVo searchVo) throws Exception;
	
	/**
	 * 查詢宝贝线图
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年9月30日 上午4:20:49 
	 */
	public RetResult<?> findLine(HotPurposeSearchVo searchVo) throws Exception;
	
	/**
	 * 清除redis缓存
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年10月3日 下午11:37:20 
	 */
	public RetResult<?> delCache(HotPurposeSearchVo searchVo) throws Exception;
}

package com.zdata.yundong.service;

import com.zdata.yundong.model.HotWordsCollect;
import com.zdata.yundong.result.RetResult;

public interface HotWordsCollectService {

	/**
	 * 保存
	 * @param:        @param record
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月23日 下午11:59:03 
	 */
	public RetResult<?> save(HotWordsCollect record) throws Exception;
	
	/**
	 * 查询收藏列表
	 * @param:        @param record
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月23日 下午11:59:15 
	 */
	public RetResult<?> find(HotWordsCollect record) throws Exception;
	
	/**
	 * 取消收藏
	 * @param:        @param id
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月23日 下午11:59:28 
	 */
	public RetResult<?> cancel(Integer id) throws Exception;
	
	/**
	 * 查询收藏的关键词
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月24日 下午7:48:55 
	 */
	public RetResult<?> findWord() throws Exception;
}

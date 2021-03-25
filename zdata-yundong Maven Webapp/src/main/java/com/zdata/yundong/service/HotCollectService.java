package com.zdata.yundong.service;

import com.zdata.yundong.model.HotCollect;
import com.zdata.yundong.result.RetResult;

public interface HotCollectService {

	/**
	 * 保存
	 * @param:        @param hotCollect
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月22日 下午10:56:27
	 */
	public RetResult<?> save(HotCollect record) throws Exception;
	
	/**
	 * 查询收藏列表
	 * @param:        @param record
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月23日 下午4:13:10 
	 */
	public RetResult<?> find(HotCollect record) throws Exception;
	
	/**
	 * 取消收藏
	 * @param:        @param id
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月23日 下午4:38:15 
	 */
	public RetResult<?> cancel(Integer id) throws Exception; 
}

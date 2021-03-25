package com.zdata.yundong.service;

import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.vo.search.GroupSurgeSearchVo;

public interface GroupSurgeService {

	/**
	 * 查询某一个热销产品的信息
	 * 1.该条拼团飙升榜的数据详情信息
	 * 2.该条拼团飙升榜访客指数随时间的增长曲线
	 * 3.该条拼团飙升榜订单指数随时间的增长曲线
	 * 4.该条拼团飙升榜访客占当天大盘比值的增长曲线
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月20日 下午4:13:17 
	 */
	public RetResult<?> findDetail(GroupSurgeSearchVo searchVo) throws Exception;
	
	/**
	 * 查询列表
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年9月26日 下午11:07:41 
	 */
	public RetResult<?> findListByCatId(GroupSurgeSearchVo searchVo) throws Exception;
	
	/**
	 * 查询相关的数据
	 * @param:        @param searchVo
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年9月27日 下午5:21:53 
	 */
	public RetResult<?> findRelated(GroupSurgeSearchVo searchVo) throws Exception;
}

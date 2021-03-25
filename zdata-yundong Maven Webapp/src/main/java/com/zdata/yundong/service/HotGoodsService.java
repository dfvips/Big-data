package com.zdata.yundong.service;

import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.vo.search.HotGoodsSearchVo;

public interface HotGoodsService {
 
	/**
	 * catId查询统计
	 * @param:        @param catId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月17日 下午1:59:11 
	 */
	public RetResult<?> findByCatId(Integer catId) throws Exception;
	
	/**
	 * goodsName查询
	 * @param:        @param goodsName
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月17日 下午6:54:16 
	 */
	public RetResult<?> findInfoByGoodsName(String goodsName) throws Exception;
	
	/**
	 * 根据catId查询随时间增值的数据
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月17日 下午8:21:41 
	 */
	public RetResult<?> findListByCatId(HotGoodsSearchVo searchVo) throws Exception;
	
	/**
	 * 查询饼图
	 * @param:        @param catId
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月17日 下午10:38:13 
	 */
	public RetResult<?> findPieByCatId(Integer catId) throws Exception;
	
	/**
	 * 查询饼图，上榜链接占当天大盘情况
	 * @param:        @param catId
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月18日 下午11:03:04 
	 */
	public RetResult<?> findPieCatId(Integer catId) throws Exception;
	
	/**
	 * 获取updateDate前一天以前新上榜的数据
	 * 说明：系统数据库上榜单表上没有该数据，新增加的榜单。可做重点分析的对象
	 * @param:        @param updateDate
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月19日 下午2:10:39 
	 */
	public void updateRise(String updateDate) throws Exception;
	
	/**
	 * 查询某一个热销产品的信息
	 * 1.该条热销榜的数据详情信息
	 * 2.该条热销榜访客指数随时间的增长曲线
	 * 3.该条热销榜订单指数随时间的增长曲线
	 * 4.该条热销榜访客占当天大盘比值的增长曲线
	 * 5.该条热销榜订单占当天大盘比值的增长曲线
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月20日 下午4:13:17 
	 */
	public RetResult<?> findDetail(HotGoodsSearchVo searchVo) throws Exception;
	
	/**
	 * 较比查询时间前一天的数据，前一天数据不存在，设置默认进0名次。
	 * 若前天数据存在。用当前名次-前天名次获取进名次数值，数值为负数则是有可能是潜在爆款，数值越小，潜在爆款越爆
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年9月14日 下午10:05:49 
	 */
	public RetResult<?> findProgress(HotGoodsSearchVo searchVo) throws Exception;
	
	/**
	 * 查询连续上榜三天
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年9月24日 下午5:25:50 
	 */
	public RetResult<?> findHot(HotGoodsSearchVo searchVo) throws Exception;
	
	/**
	 * 更新排名
	 * @param:        @param updateDate
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年9月24日 下午11:13:08 
	 */
	public void updateRowNum(String updateDate) throws Exception;
	
	/**
	 * 查询相关的数据
	 * @param:        @param searchVo
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年9月27日 下午5:21:53 
	 */
	public RetResult<?> findRelated(HotGoodsSearchVo searchVo) throws Exception;
	
}

package com.zdata.yundong.service;

import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.vo.search.HotWordsSearchVo;

public interface HotWordsService {

	/**
	 * 查询列表
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月18日 下午5:09:21 
	 */
	public RetResult<?> find(HotWordsSearchVo searchVo) throws Exception;
	
	/**
	 * 
	 * @param:        @param catId
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月18日 下午9:14:26 
	 */
	public RetResult<?> findByCatId(Integer catId) throws Exception;
	
	/**
	 * 查询结果值
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月21日 下午4:32:40 
	 */
	public RetResult<?> findListByCatId(HotWordsSearchVo searchVo) throws Exception;
	
	/**
	 * 查询详情;
	 * 1.该条搜索热词的数据详情信息
	 * 2.该条搜索热词搜索热度随时间的增长曲线
	 * 3.该条搜索热词点击热度随时间的增长曲线
	 * 4.该条搜索热词竞争强度随时间的增长曲线
	 * 5.该条搜索热词搜索热度占当天大盘比值的增长曲线
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月21日 下午5:53:30 
	 */
	public RetResult<?> findDetail(HotWordsSearchVo searchVo) throws Exception;
	
	/**
	 * 获取updateDate前一天以前新上榜的数据
	 * 说明：系统数据库热词表上没有该数据，新增加的热销词汇。可做重点分析的对象
	 * @param:        @param updateDate
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月19日 下午2:10:39 
	 */
	public void updateRise(String updateDate) throws Exception;
	
	/**
	 * 类目Id查询计算蓝海词
	 * @param:        @param catId
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月26日 下午1:25:06 
	 */
	public void findYoung(Integer catId) throws Exception;
	
	/**
	 * 查询表格数据
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年10月2日 上午3:46:35 
	 */
	public RetResult<?> findInfo(HotWordsSearchVo searchVo) throws Exception;
	
	/**
	 * 查询相似度超过0.7的词
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年10月2日 上午4:50:57 
	 */
	public RetResult<?> findSame(HotWordsSearchVo searchVo) throws Exception;
}

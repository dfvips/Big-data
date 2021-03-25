package com.zdata.ccc.service;

import com.zdata.ccc.result.RetResult;
import com.zdata.ccc.vo.search.WordTrendSearchVo;

public interface WordTrendService {

	/**
	 * 查询详情;
	 * 1.该条搜索热词的数据详情信息
	 * 2.该条搜索热词搜索热度占当天大盘比值的增长曲线
	 * 3.该条搜索热词点击热度随时间的增长曲线
	 * 4.该条搜索热词竞争强度随时间的增长曲线
	 * @param:        @param wordId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月25日 下午6:43:47 
	 */
	public RetResult<?> findDetail(WordTrendSearchVo searchVo) throws Exception;
	
}

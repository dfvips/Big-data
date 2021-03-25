package com.zdata.ccc.service;

public interface TimeService {

	/**
	 * 定时更新蓝海热词，查询方法，查询新增热搜索词，不在youngword出现过并且不在常量表constant
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月29日 下午7:12:54
	 */
	public void updateYoungWord() throws Exception;
	
	/**
	 * 定时更新新上榜数据
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年9月12日 下午4:57:07 
	 */
	public void updateRise() throws Exception;
}

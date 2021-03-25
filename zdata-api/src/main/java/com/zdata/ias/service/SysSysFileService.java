package com.zdata.ias.service;

import java.util.Map;

public interface SysSysFileService {

	/**
	 * 删除业务附件信息
	 * @param:        @param busType
	 * @param:        @param id
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年2月9日 下午6:30:12 
	 */
	public Map<String,Object> deleteByBus(String busType,int busId);
	
}

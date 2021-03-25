package com.zdata.yundong.service;

import com.zdata.yundong.model.GroupRise;
import com.zdata.yundong.result.RetResult;
import com.zdata.yundong.vo.search.GroupRiseSearchVo;

public interface GroupRiseService {

	public void updateRise(String riseDate) throws Exception;
	
	public RetResult<?> find(GroupRiseSearchVo searchVo) throws Exception;
	
	public RetResult<?> save(GroupRise record) throws Exception;
	
	public RetResult<?> updateTag(GroupRise record) throws Exception;
	
	/**
	 * 查询线图
	 * @param:        @param searchVo
	 * @param:        @return
	 * @param:        @throws Exception    
	 * @author:       梦丶随心飞
	 * @Date:         2020年9月10日 下午10:45:44 
	 */
	public RetResult<?> findLine(GroupRiseSearchVo searchVo) throws Exception;
}

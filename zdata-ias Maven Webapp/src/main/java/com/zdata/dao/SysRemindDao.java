package com.zdata.dao;

import java.util.List;
import java.util.Map;

import com.zdata.model.SysRemind;

public interface SysRemindDao {

	int add(SysRemind sysRemind);

    int update(SysRemind sysRemind);

    int delete(int id);

    SysRemind findById(int id);

    List<SysRemind> findAllList(Map<String,Object> param);
	
	/**
	 * 查询记录
	 * @param:        @param record
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年5月30日 下午7:10:52 
	 */
	List<SysRemind> findByAll(SysRemind record);
	
	int deleteByFuncId(String funcId);
	
}

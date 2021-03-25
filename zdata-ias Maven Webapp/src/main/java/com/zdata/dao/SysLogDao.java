package com.zdata.dao;

import java.util.List;
import java.util.Map;

import com.zdata.model.PageBean;
import com.zdata.model.SysLog;

/**
* sys_log
* @author 梦丶随心飞 2020-07-21
*/
public interface SysLogDao {
	
	int add(SysLog sysLog);

    int update(SysLog sysLog);

    int delete(int id);

    SysLog findById(int id);

    List<SysLog> findAllList(Map<String,Object> param);
    
    List<SysLog> find(SysLog sysLog,PageBean pageBean);
    
    int count(SysLog sysLog,PageBean pageBean);
}

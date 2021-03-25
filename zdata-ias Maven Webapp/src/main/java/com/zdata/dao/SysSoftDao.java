package com.zdata.dao;

import java.util.List;
import java.util.Map;

import com.zdata.model.SysSoft;

/**
* sys_soft
* @author 梦丶随心飞 2020-07-18
*/
public interface SysSoftDao {

	int add(SysSoft sysSoft);

    int update(SysSoft sysSoft);

    int delete(int id);

    SysSoft findById(int id);

    List<SysSoft> findAllList(Map<String,Object> param);
    
    SysSoft findBySoftId(String softId);
    
    List<SysSoft> findByAll(SysSoft record);
    
    int findCountByAll(SysSoft record);
    
}

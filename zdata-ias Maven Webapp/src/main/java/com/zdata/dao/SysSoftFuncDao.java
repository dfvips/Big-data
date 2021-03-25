package com.zdata.dao;

import java.util.List;
import java.util.Map;

import com.zdata.model.SysSoftFunc;

/**
* 软件注册预设权限表
* @author 梦丶随心飞 2020-07-14
*/
public interface SysSoftFuncDao {

	int add(SysSoftFunc sysSoftFunc);

    int update(SysSoftFunc sysSoftFunc);

    int delete(int id);

    SysSoftFunc findById(int id);

    List<SysSoftFunc> findAllList(Map<String,Object> param);
    
    int deleteBySoftId(Integer softId);
    
    List<SysSoftFunc> findBySoftId(Integer softId);
    
    int deleteByFuncId(String funcId);
}

package com.zdata.dao;

import java.util.List;
import java.util.Map;

import com.zdata.model.SysRoleFunc;

/**
* 角色权限
* @author 梦丶随心飞 2020-07-07
*/
public interface SysRoleFuncDao {

	int add(SysRoleFunc sysRoleFunc);

    int update(SysRoleFunc sysRoleFunc);

    int delete(int id);

    SysRoleFunc findById(int id);

    List<SysRoleFunc> findAllList(Map<String,Object> param);
    
    int deleteByRoleId(int roleId);
    
    List<SysRoleFunc> findAllByRoleIdList(int roleId);
    
    int deleteByFuncId(String funcId);
    
}

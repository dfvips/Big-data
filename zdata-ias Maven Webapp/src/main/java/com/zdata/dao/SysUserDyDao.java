package com.zdata.dao;

import java.util.List;
import java.util.Map;

import com.zdata.model.SysUserDy;

/**
* sys_user_dy
* @author 梦丶随心飞 2020-08-04
*/
public interface SysUserDyDao {

	int add(SysUserDy sysUserDy);

    int update(SysUserDy sysUserDy);

    int delete(int id);

    SysUserDy findById(int id);

    List<SysUserDy> findAllList(Map<String,Object> param);
    
    SysUserDy findByOpenId(String openId);
    
    SysUserDy findByUserId(String userId);
}

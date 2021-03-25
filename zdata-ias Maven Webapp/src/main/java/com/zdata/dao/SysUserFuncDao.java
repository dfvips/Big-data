package com.zdata.dao;

import java.util.List;
import java.util.Map;

import com.zdata.model.SysUserFunc;

/**
* sys_user_func
* @author 梦丶随心飞 2020-07-19
*/
public interface SysUserFuncDao {
	
	int add(SysUserFunc sysUserFunc);

    int update(SysUserFunc sysUserFunc);

    int delete(int id);

    SysUserFunc findById(int id);

    List<SysUserFunc> findAllList(Map<String,Object> param);
    
    /**
     * 获取用户权限
     * @param:        @param userId
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年7月19日 下午6:07:28 
     */
    List<SysUserFunc> findByUserId(String userId);
    
    /**
     * 用户Id删除
     * @param:        @param userId
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年7月19日 下午6:21:38 
     */
    int deleteByUserId(String userId);
    
    /**
     * 
     * @param:        @param funcId
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年7月25日 下午8:12:19 
     */
    int deleteByFuncId(String funcId);
}

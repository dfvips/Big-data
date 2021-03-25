package com.zdata.dao;

import java.util.List;
import java.util.Map;

import com.zdata.model.SysFunc;

/**
* sys_func
* @author 梦丶随心飞 2020-07-21
*/
public interface SysFuncDao {

	int add(SysFunc sysFunc);

    int update(SysFunc sysFunc);

    int delete(int id);

    SysFunc findById(int id);

    List<SysFunc> findAllList(Map<String,Object> param);
    
    SysFunc findByFuncId(String funcId);
    
    boolean isParent(String funcId,String userId);
    
    /**
	 * 父节点和用户Id查询功能菜单
	 * <p>Title: findByPidAndUserId</p>  
	 * <p>Description: </p>  
	 * @param funcId
	 * @param userId
	 * @return
	 */
	List<SysFunc> findByPidAndUserId(String funcId,String userId,HttpServletRequest request);
	
	/**
	 * 
	 * @param:        @param pId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月22日 下午10:29:49 
	 */
	List<SysFunc> findLikeByPid(String pId);
	
	int deleteByFuncId(String funcId);
	
	/**
	 * 
	 * @param:        @param funcId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月25日 下午10:37:34 
	 */
	List<SysFunc> findLikeByFuncId(String funcId);
	
	/**
	 * 
	 * @param:        @param funcId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年7月25日 下午10:57:23 
	 */
	boolean isParent(String funcId);
	
	List<SysFunc> findByPid(String pId);
}

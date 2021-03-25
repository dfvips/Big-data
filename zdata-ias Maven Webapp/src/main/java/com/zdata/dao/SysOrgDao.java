package com.zdata.dao;

import java.util.List;
import java.util.Map;

import com.zdata.model.SysOrg;

/**
 * 
* <p>Title: OrgDao</p>  
* <p>Description: </p>  
* @author 梦丶随心飞
* @date 2019年6月9日
 */
public interface SysOrgDao {

	int add(SysOrg sysOrg);

    int update(SysOrg sysOrg);

    int delete(String orgId);

    SysOrg findByOrgId(String orgId);

    List<SysOrg> findAllList(Map<String,Object> param);
    
	/**
	 * 判断节点下是否有孩子节点，有返回ture
	 * <p>Title: isFile</p>  
	 * <p>Description: </p>  
	 * @param orgId
	 * @return
	 */
	public boolean isParent(String orgId);
	
	/**
	 * 
	 * @param:        @param orgId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年5月23日 下午12:13:12 
	 */
	public List<SysOrg> findByPid(String orgId);
	
	/**
	 * 显示状态和父节点id查询子树
	 * <p>Title: findByPidAndIsShow</p>  
	 * <p>Description: </p>  
	 * @param orgId
	 * @param isShow
	 * @return
	 */
	public List<SysOrg> findByPidAndIsShow(String orgId,String isShow);
	
	public List<SysOrg> pageTree(List<SysOrg> orgList);
	
	public void setColumn(String columnName,String columnValue,String id);//更新字段
	
	/**
	 * 我的部门根节点
	 * <p>Title: loadBySoftIdAndUserId</p>  
	 * <p>Description: </p>  
	 * @param softId
	 * @param userId
	 * @return
	 */
	public SysOrg loadBySoftIdAndUserId(String softId,String userId);
}

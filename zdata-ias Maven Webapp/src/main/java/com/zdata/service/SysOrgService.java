package com.zdata.service;

import java.util.List;
import java.util.Map;

import com.zdata.model.SysOrg;

/**
 * 
* <p>Title: OrgService</p>  
* <p>Description: </p>  
* @author 梦丶随心飞
* @date 2019年6月9日
 */
public interface SysOrgService {

	/**
	 * 
	 * <p>Title: isParent</p>  
	 * <p>Description: </p>  
	 * @param orgId
	 * @return
	 */
	public boolean isParent(String orgId);
	
	/**
	 * 软件id下组织结构
	 * <p>Title: findByPid</p>  
	 * <p>Description: </p>  
	 * @param pId
	 * @return
	 */
	public Map<String, Object> findByPid(String orgId);
	
	/**
	 * 显示状态和父节点id查询子树
	 * <p>Title: findByPidAndIsShow</p>  
	 * <p>Description: </p>  
	 * @param orgId
	 * @param isShow
	 * @return
	 */
	public List<SysOrg> findByPidAndIsShow(String orgId,String isShow);
	
	/**
	 * 
	 * <p>Title: add</p>  
	 * <p>Description: </p>  
	 * @param org
	 */
	public void add(SysOrg org);
	
	/**
	 * 
	 * <p>Title: update</p>  
	 * <p>Description: </p>  
	 * @param org
	 */
	public void update(SysOrg org);
	
	/**
	 * 
	 * <p>Title: delete</p>  
	 * <p>Description: </p>  
	 * @param id
	 */
	public void delete(String id);//删除

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

	/**
	 * 
	 * @param:        @param orgId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年5月22日 下午10:21:14 
	 */
	public Map<String, Object> loadByOrgId(String orgId);
	
	/**
	 * 保存
	 * @param:        @param org
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年5月23日 上午10:07:43 
	 */
	public Map<String, Object> save(SysOrg org,HttpServletRequest request);
	
}

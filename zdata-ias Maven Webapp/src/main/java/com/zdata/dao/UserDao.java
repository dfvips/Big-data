package com.zdata.dao;

import java.util.List;

import com.zdata.model.PageBean;
import com.zdata.model.User;

/**
 * 用户接口
 * @author 梦丶随心飞
 *
 */
public interface UserDao {

	/**
	 * 根据软件id和状态检索用户列表
	 * <p>Title: findBySoftId</p>  
	 * <p>Description: </p>  
	 * @param pageBean
	 * @param searchUser
	 * @param softId
	 * @param state
	 * @return
	 */
	public List<User> findBySoftId(PageBean pageBean,User searchUser,String softId,String state);
	
	/**
	 * 
	 * <p>Title: countBySoftId</p>  
	 * <p>Description: </p>  
	 * @param searchUser
	 * @param softId
	 * @param state
	 * @return
	 */
	public int countBySoftId(User searchUser,String softId,String state);
	
	/**
	 * 
	 * <p>Title: loadByrowId</p>  
	 * <p>Description: </p>  
	 * @param rowId
	 * @return
	 */
	public User loadByrowId(int rowId);
	
	/**
	 * 添加用户
	 * <p>Title: add</p>  
	 * <p>Description: </p>  
	 * @param user
	 * @param softId
	 */
	public void add(User user);
	
	/**
	 * 组织id查找用户列表
	 * <p>Title: findByOrgId</p>  
	 * <p>Description: </p>  
	 * @param pageBean
	 * @param search
	 * @param orgId
	 * @return
	 */
	public List<User> findByOrgId(PageBean pageBean,String search,String orgId,boolean isLike);
	
	/**
	 * 
	 * <p>Title: countByOrgId</p>  
	 * <p>Description: </p>  
	 * @param search
	 * @param orgId
	 * @return
	 */
	public int countByOrgId(String search,String orgId,boolean isLike);
	
	/**
	 * 
	 * <p>Title: loadByUserId</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @return
	 */
	public User loadByUserId(String userId);
	
	
	/**
	 * 判断部门节点下是否存在用户
	 * <p>Title: existUserByOrgId</p>  
	 * <p>Description: </p>  
	 * @param orgId
	 * @return
	 */
	public boolean existUserByOrgId(String orgId);
	
	/**
	 * 更新字段
	 * <p>Title: setColumn</p>  
	 * <p>Description: </p>  
	 * @param columnName
	 * @param columnValue
	 * @param id
	 */
	public void saveColumn(String columnName,String columnValue,int id);
	
	/**
	 * 手机号码查询
	 * <p>Title: loadByMobtele</p>  
	 * <p>Description: </p>  
	 * @param mobTele
	 * @return
	 */
	public List<User> findByMobtele(String mobTele);
	
	/**
	 * 更新用户
	 * <p>Title: update</p>  
	 * <p>Description: </p>  
	 * @param user
	 */
	public void update(User user);
	
	/**
	 * 判断用户Id是否存在
	 * <p>Title: existUserByUserId</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @param rowId
	 * @return
	 */
	public boolean existUserByUserId(String userId,Integer rowId);
	
	/**
	 * 查询全部账户
	 * @param softId
	 * @return
	 */
	public List<User> findAccounts(String softId);
	
}

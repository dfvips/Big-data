package com.zdata.service;

import java.util.List;
import java.util.Map;

import com.zdata.model.PageBean;
import com.zdata.model.User;

/**
 * 用户Servcie接口
 * @author 梦丶随心飞
 *
 */
public interface UserService {

	public List<User> findBySoftId(PageBean pageBean,User s_User,String softId,String state);//根据软件id和状态检索用户列表
	
	public int countBySoftId(User s_User,String softId,String state);//
	
	public User loadByrowId(int rowId);
	
	/**
	 * 组织id查找用户列表
	 * <p>Title: findByOrgId</p>  
	 * <p>Description: </p>  
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
	 * 
	 * <p>Title: save</p>  
	 * <p>Description: </p>  
	 * @param user
	 * @return
	 */
	public Map<String, Object> save(User user,HttpServletRequest request);
	
	/**
	 * <p>Title: existUserByUserId</p>  
	 * <p>Description: </p>  
	 * @param userId
	 * @param rowId
	 * @return
	 */
	public Map<String, Object> existUserByUserId(String userId,Integer rowId);
	
	/**
	 * 
	 * <p>Title: loadById</p>  
	 * <p>Description: </p>  
	 * @param rowId
	 * @return
	 */
	public Map<String, Object> loadById(Integer rowId);
	
	/**
	 * 修改用户密码
	 * <p>Title: upPassword</p>  
	 * <p>Description: </p>  
	 * @param newPassword
	 * @return
	 */
	public Map<String, Object> setPassword(String newPassword,HttpServletRequest request);
	
}

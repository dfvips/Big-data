package com.zdata.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdata.constant.Constant;
import com.zdata.dao.UserDao;
import com.zdata.model.PageBean;
import com.zdata.model.SysUser;
import com.zdata.model.User;
import com.zdata.service.UserService;
import com.zdata.util.EncryptUtil;
import com.zdata.util.SessionUtils;

/**
 * 信息Service实现层
 * @author 梦丶随心飞
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;
	
	@Override
	public List<User> findBySoftId(PageBean pageBean, User searchUser,String softId, String state) {
		return userDao.findBySoftId(pageBean, searchUser, softId, state);
	}

	@Override
	public int countBySoftId(User searchUser, String softId, String state) {
		return userDao.countBySoftId(searchUser, softId, state);
	}

	@Override
	public User loadByrowId(int rowId) {
		return userDao.loadByrowId(rowId);
	}

	@Override
	public List<User> findByOrgId(PageBean pageBean, String search, String orgId, boolean isLike) {
		return userDao.findByOrgId(pageBean, search, orgId, isLike);
	}

	@Override
	public int countByOrgId(String search, String orgId, boolean isLike) {
		return userDao.countByOrgId(search, orgId, isLike);
	}

	@Override
	public User loadByUserId(String userId) {
		return userDao.loadByUserId(userId);
	}

	@Override
	public boolean existUserByOrgId(String orgId) {
		return userDao.existUserByOrgId(orgId);
	}

	@Override
	public void saveColumn(String columnName, String columnValue, int id) {
		userDao.saveColumn(columnName, columnValue, id);		
	}

	@Override
	public List<User> findByMobtele(String mobTele) {
		return userDao.findByMobtele(mobTele);
	}

	@Override
	public Map<String, Object> save(User user,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysUser curUser = SessionUtils.getCurrentUser(request);
			//判断用户
			if(user.getRowId()==null){
				//判断登录号是否重复
				if(userDao.existUserByUserId(user.getSysUserId(), null)){
					map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
					map.put(Constant.Result_Msg.getValue(), "用户登录号重复！");
				}else{
					user.setCreate(curUser.getUserId());
					user.setSysSoftId(curUser.getSoftId());
					//设置用户密码
					user.setSysUserPassword(EncryptUtil.encrypt(user.getSysUserPassword(), user.getSysUserId()));
					userDao.add(user);
					map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
				}
			}else {
				if(userDao.existUserByUserId(user.getSysUserId(), user.getRowId())){
					map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
					map.put(Constant.Result_Msg.getValue(), "用户登录号重复！");
				}else{
					user.setCreate(curUser.getUserId());
					User cerUser = userDao.loadByrowId(user.getRowId());
					if (!cerUser.getSysUserPassword().equals(user.getSysUserPassword())) {
						user.setSysUserPassword(EncryptUtil.encrypt(user.getSysUserPassword(), user.getSysUserId()));
					}
					userDao.update(user);
					map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
				}
			}
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			map.put(Constant.Result_Msg.getValue(), "系统错误！");
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> existUserByUserId(String userId, Integer rowId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			boolean falg = userDao.existUserByUserId(userId, rowId);
			map.put("status", "success");
			map.put("isExist", falg);
		} catch (Exception e) {
			map.put("status", "fail");
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> loadById(Integer rowId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			User user = userDao.loadByrowId(rowId);
			map.put(Constant.Row_Name.getValue(), user);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> setPassword(String newPassword,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HttpSession session = request.getSession(true);
			User user = (User) session.getAttribute(Constant.Current_User.getValue());
			String password= EncryptUtil.encrypt(newPassword, user.getSysUserId());
			//判断是否与原始密码相同
			if(password.equals(user.getSysUserPassword())){
				map.put(Constant.Status_Name.getValue(),Constant.FAIL.getValue());
				map.put(Constant.Result_Msg.getValue(), "新设置密码与旧密码相同");
			} else {
				userDao.saveColumn("SYSUSERPASSWORD", password, user.getRowId());
				map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
			}
		} catch (NumberFormatException e) {
			map.put(Constant.Status_Name.getValue(),Constant.FAIL.getValue());
			map.put(Constant.Result_Msg.getValue(), "系统错误！");
			e.printStackTrace();
		}
		return map;
	}

}

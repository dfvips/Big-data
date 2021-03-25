package com.zdata.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdata.constant.Constant;
import com.zdata.dao.SysRoleFuncDao;
import com.zdata.dao.SysRoleInfoDao;
import com.zdata.dao.SysUserDao;
import com.zdata.dao.SysUserFuncDao;
import com.zdata.model.PageBean;
import com.zdata.model.SysRoleFunc;
import com.zdata.model.SysRoleInfo;
import com.zdata.model.SysUser;
import com.zdata.model.SysUserFunc;
import com.zdata.service.SysUserService;
import com.zdata.util.EncryptUtil;
import com.zdata.util.SessionUtils;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired
	private SysRoleFuncDao sysRoleFuncDao;
	
	@Autowired
	private SysUserFuncDao sysUserFuncDao;
	
	@Autowired
	private SysRoleInfoDao sysRoleInfoDao;
	
	@Override
	public List<SysUser> findByOrgId(PageBean pageBean, String search, String orgId, boolean isLike) {
		return sysUserDao.findByOrgId(pageBean, search, orgId, isLike);
	}

	@Override
	public int countByOrgId(String search, String orgId, boolean isLike) {
		return sysUserDao.countByOrgId(search, orgId, isLike);
	}

	@Override
	public SysUser loadByUserId(String userId) {
		return sysUserDao.loadByUserId(userId);
	}

	@Override
	public Map<String, Object> loadById(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysUser user = sysUserDao.findById(id);
			map.put(Constant.Row_Name.getValue(), user);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> save(SysUser sysUser, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysUser loginUser = SessionUtils.getCurrentUser(request);
			SysUser user = sysUserDao.loadByUserId(sysUser.getUserId());
			//判断用户
			if(sysUser.getId()==null){
				if(user!=null){
					//判断登录号是否重复
					map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
					map.put(Constant.Result_Msg.getValue(), "用户登录号重复！");
				}else{
					sysUser.setCreateName(loginUser.getUserId());
					sysUser.setExchangDate(new Date());
					sysUser.setUserPassword(EncryptUtil.encrypt(sysUser.getUserPassword(), sysUser.getUserId()));
					sysUser.setFlowPassword(Constant.Flow_Password.getValue());
					sysUserDao.add(sysUser);
					//角色上添加用户
					SysRoleInfo info = new SysRoleInfo(sysUser.getRoleId(), sysUser.getUserId());
					sysRoleInfoDao.add(info);
					//用户设置权限
					Integer roleId = sysUser.getRoleId();
					List<SysRoleFunc> list = sysRoleFuncDao.findAllByRoleIdList(roleId);
					for (SysRoleFunc sysRoleFunc : list) {
						SysUserFunc func = new SysUserFunc(sysUser.getUserId(), sysRoleFunc.getFuncId());
						sysUserFuncDao.add(func);
					}
					map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
				}
			}else {
				if(user!=null){
					//判断登录号是否重复
					map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
					map.put(Constant.Result_Msg.getValue(), "用户登录号重复！");
				}else{
					SysUser curUser = sysUserDao.findById(sysUser.getId());
					if (!curUser.getUserPassword().equals(sysUser.getUserPassword())) {
						sysUser.setUserPassword(EncryptUtil.encrypt(sysUser.getUserPassword(), sysUser.getUserId()));
					}
					sysUserDao.update(sysUser);
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
	public Map<String, Object> deleteById(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysUser user = sysUserDao.findById(id);
			//刪除角色用戶
			sysRoleInfoDao.deleteByUserId(user.getUserId());
			//删除用户权限
			sysUserFuncDao.deleteByUserId(user.getUserId());
			//删除用户
			sysUserDao.delete(id);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public SysUser loadByMobtele(String mobtele) {
		return sysUserDao.loadByMobtele(mobtele);
	}

}

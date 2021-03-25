package com.zdata.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zdata.constant.Constant;
import com.zdata.dao.SysRoleFuncDao;
import com.zdata.dao.SysRoleInfoDao;
import com.zdata.dao.SysUserFuncDao;
import com.zdata.model.SysRoleFunc;
import com.zdata.model.SysRoleInfo;
import com.zdata.model.SysUserFunc;
import com.zdata.service.SysRoleInfoService;

@Service("sysRoleInfoService")
public class SysRoleInfoServiceImpl implements SysRoleInfoService {

	@Autowired
	private SysRoleInfoDao sysRoleInfoDao;
	
	@Autowired
	private SysRoleFuncDao sysRoleFuncDao;
	
	@Autowired
	private SysUserFuncDao sysUserFuncDao;
	
	@Override
	public Map<String, Object> findByRoleId(Integer roleId,Integer page,Integer rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			page=((page-1)*rows);
			List<SysRoleInfo> sysRoleInfoList = sysRoleInfoDao.findByRoleId(roleId,page,rows);
			map.put(Constant.Rows_Name.getValue(), sysRoleInfoList);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> delete(Integer[] ids,String[] userIds,Integer roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			for (String userId : userIds) {
				//查询用户拥有的角色
				List<SysRoleInfo> infos = sysRoleInfoDao.findByUserId(userId);
				//删除用户权限
				sysUserFuncDao.deleteByUserId(userId);
				//不止一种角色
				if (infos!=null&&infos.size()>1) {
					Set<String> funcsSet = new HashSet<String>();
					for (SysRoleInfo sysRoleInfo : infos) {
						//角色Id
						Integer theRoleId = sysRoleInfo.getRoleId();
						if (theRoleId!=roleId) {
							List<SysRoleFunc> theFuncs = sysRoleFuncDao.findAllByRoleIdList(theRoleId);
							for (SysRoleFunc sysRoleFunc : theFuncs) {
								funcsSet.add(sysRoleFunc.getFuncId());
							}
						}
					}
					for (String funcId : funcsSet) {
						SysUserFunc sysUserFunc = new SysUserFunc();
						sysUserFunc.setFuncId(funcId);
						sysUserFunc.setUserId(userId);
						sysUserFuncDao.add(sysUserFunc);
					}
				}
			}
			//删除角色详情
			sysRoleInfoDao.delete(ids);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> saveUsers(String[] users, Integer roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//获取当前角色权限
			List<SysRoleFunc> funcs = sysRoleFuncDao.findAllByRoleIdList(roleId);
			for (int i = 0; i < users.length; i++) {
				//非新添加的角色用户
				if (!sysRoleInfoDao.exitByUser(roleId, users[i])) {
					SysRoleInfo sysRoleInfo = new SysRoleInfo(roleId, users[i]);
					sysRoleInfoDao.add(sysRoleInfo);
				}
				String userId = users[i];
				//获取用户角色：一个用户可能有多个角色
				List<SysRoleInfo> infos = sysRoleInfoDao.findByUserId(userId);
				Set<String> funcsSet = new HashSet<String>();
				for (SysRoleInfo sysRoleInfo : infos) {
					Integer theRoleId = sysRoleInfo.getRoleId();
					if(theRoleId!=roleId){
						List<SysRoleFunc> theFuncs = sysRoleFuncDao.findAllByRoleIdList(theRoleId);
						for (SysRoleFunc sysRoleFunc : theFuncs) {
							funcsSet.add(sysRoleFunc.getFuncId());
						}
					}
				}
				if(funcs!=null&&funcs.size()>0){
					for (SysRoleFunc sysRoleFunc : funcs) {
						funcsSet.add(sysRoleFunc.getFuncId());
					}
				}
				
				//删除用户权限
				sysUserFuncDao.deleteByUserId(userId);
				for (String funcId : funcsSet) {
					SysUserFunc sysUserFunc = new SysUserFunc();
					sysUserFunc.setFuncId(funcId);
					sysUserFunc.setUserId(userId);
					sysUserFuncDao.add(sysUserFunc);
				}
			}
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

}

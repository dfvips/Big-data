package com.zdata.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zdata.constant.Constant;
import com.zdata.dao.SysRoleDao;
import com.zdata.dao.SysRoleFuncDao;
import com.zdata.dao.SysRoleInfoDao;
import com.zdata.dao.SysUserFuncDao;
import com.zdata.model.SysRole;
import com.zdata.model.SysRoleFunc;
import com.zdata.model.SysRoleInfo;
import com.zdata.model.SysUserFunc;
import com.zdata.service.SysRoleInfoService;
import com.zdata.service.SysRoleService;

@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Autowired
	private SysRoleInfoDao sysRoleInfoDao;
	
	@Autowired
	private SysRoleInfoService sysRoleInfoService;
	
	@Autowired
	private SysRoleFuncDao sysRoleFuncDao;
	
	@Autowired
	private SysUserFuncDao sysUserFuncDao;
	
	@Override
	public Map<String, Object> findByAll(SysRole record) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (record.getPage()==null) {
				record.setPage(0);
				record.setRows(Integer.MAX_VALUE);
			}else{
				record.setPage((record.getPage()-1)*record.getRows());
			}
			List<SysRole> sysRoleList = sysRoleDao.findByAll(record);
			int total =sysRoleDao.findCountByAll(record);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
			map.put(Constant.Rows_Name.getValue(), sysRoleList);
			map.put(Constant.Total_Name.getValue(), total);
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> save(SysRole record) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (record.getId()==null) {
				sysRoleDao.add(record);
			}else {
				sysRoleDao.update(record);
			}
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> delete(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//删除角色明细
			List<SysRoleInfo> list = sysRoleInfoDao.findByRoleId(id, 0, Integer.MAX_VALUE);
			if (list!=null&&list.size()>0) {
				Integer[] ids = new Integer[list.size()];
				String[] userIds = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					ids[i] = list.get(i).getId();
					userIds[i] = list.get(i).getUserId();
					sysRoleInfoService.delete(ids, userIds, id);
				}
			}
			sysRoleDao.delete(id);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> loadById(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysRole sysRole = sysRoleDao.loadById(id);
			List<SysRoleFunc> roleFuncList = sysRoleFuncDao.findAllByRoleIdList(id);
			sysRole.setRoleFuncList(roleFuncList);
			map.put(Constant.Row_Name.getValue(), sysRole);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> setAuth(Integer id, String[] funcIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysRoleInfo> roleInfoList = sysRoleInfoDao.findByRoleId(id, 0, Integer.MAX_VALUE);
		try {
			//刪除角色权限
			sysRoleFuncDao.deleteByRoleId(id);
			for (int j = 0; j < funcIds.length; j++) {
				SysRoleFunc sysRoleFunc = new SysRoleFunc(id, funcIds[j]);
				sysRoleFuncDao.add(sysRoleFunc);
			}
			for (int i = 0; i < roleInfoList.size(); i++) {
				//用户Id
				String userId = roleInfoList.get(i).getUserId();
				List<SysRoleInfo> infos = sysRoleInfoDao.findByUserId(userId);
				/**
				 * 该用户不止一个角色
				 */
				//删除用户权限信息
				sysUserFuncDao.deleteByUserId(userId);
				if (infos!=null&&infos.size()>1) {
					//赋值权限
					Set<String> funcIdSet = new HashSet<String>();
					//求用户拥有角色的并集合
					for (SysRoleInfo info : infos) {
						List<SysRoleFunc> list = sysRoleFuncDao.findAllByRoleIdList(info.getRoleId());
						for (SysRoleFunc sysRoleFunc : list) {
							funcIdSet.add(sysRoleFunc.getFuncId());
						}
					}
					for (String funcId : funcIdSet) {
						SysUserFunc sysUserFunc = new SysUserFunc();
						sysUserFunc.setUserId(userId);
						sysUserFunc.setFuncId(funcId);
						sysUserFuncDao.add(sysUserFunc);
					}
				} else {
					for (String funcId : funcIds) {
						SysUserFunc sysUserFunc = new SysUserFunc();
						sysUserFunc.setUserId(userId);
						sysUserFunc.setFuncId(funcId);
						sysUserFuncDao.add(sysUserFunc);
					}
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

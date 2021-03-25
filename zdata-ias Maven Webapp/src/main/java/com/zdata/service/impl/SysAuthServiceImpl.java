package com.zdata.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdata.constant.Constant;
import com.zdata.dao.SysAuthDao;
import com.zdata.dao.UserDao;
import com.zdata.model.SysAuth;
import com.zdata.model.User;
import com.zdata.service.SysAuthService;
import com.zdata.vo.SysAuthVo;

@Service("sysAuthService")
public class SysAuthServiceImpl implements SysAuthService {

	@Autowired
	private	SysAuthDao sysAuthDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public Map<String, Object> save(SysAuthVo sysAuthVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//授权用户群
			String sysUsers[] = sysAuthVo.getSysUsers().split(",");
			//授权用户所在部门
			String sysOrgs[] = sysAuthVo.getSysOrgs().split(",");
			for (int i = 0; i < sysUsers.length; i++) {
				Integer authType = sysAuthVo.getAuthType();
				SysAuth record = new SysAuth();
				List<User> userList = new ArrayList<User>();
				StringBuffer authUserIds = new StringBuffer();
				switch (authType) {
				//本人
				case 0:
					record=new SysAuth(sysUsers[i], sysAuthVo.getFuncId(), null, sysAuthVo.getAuthType(), sysUsers[i]);
					break;
				//全部
				case 1:
					record=new SysAuth(sysUsers[i], sysAuthVo.getFuncId(), null, sysAuthVo.getAuthType(), null);
					break;
				//本部
				case 2:
					userList = userDao.findByOrgId(null, null, sysOrgs[i], false);
					for (int j = 0; j < userList.size(); j++) {
						authUserIds.append(userList.get(j).getSysUserId()+",");
					}
					record=new SysAuth(sysUsers[i], sysAuthVo.getFuncId(), sysOrgs[i], sysAuthVo.getAuthType(), authUserIds.toString().substring(0, authUserIds.length()-1));
					break;
				//本部，包含子部门
				case 3:
					//部门群
					userList = userDao.findByOrgId(null, null, sysOrgs[i], true);
					for (int j = 0; j < userList.size(); j++) {
						authUserIds.append(userList.get(j).getSysUserId()+",");
					}
					record=new SysAuth(sysUsers[i], sysAuthVo.getFuncId(), sysOrgs[i], sysAuthVo.getAuthType(), authUserIds.toString().substring(0,authUserIds.length()-1));
					break;
				case 4:
					//指定部门下用户数据
					userList = userDao.findByOrgId(null, null, sysAuthVo.getOrgId(), false);
					for (int j = 0; j < userList.size(); j++) {
						authUserIds.append(userList.get(j).getSysUserId()+",");
					}
					record=new SysAuth(sysUsers[i], sysAuthVo.getFuncId(), sysAuthVo.getOrgId(), sysAuthVo.getAuthType(), authUserIds.toString().substring(0,authUserIds.length()-1));
					break;
				case 5:
					//指定部门下用户数据
					userList = userDao.findByOrgId(null, null, sysAuthVo.getOrgId(), true);
					for (int j = 0; j < userList.size(); j++) {
						authUserIds.append(userList.get(j).getSysUserId()+",");
					}
					record=new SysAuth(sysUsers[i], sysAuthVo.getFuncId(), sysAuthVo.getOrgId(), sysAuthVo.getAuthType(), authUserIds.toString().substring(0,authUserIds.length()-1));
					break;
				case 6:
					record=new SysAuth(sysUsers[i], sysAuthVo.getFuncId(), null, sysAuthVo.getAuthType(), sysAuthVo.getAuthUserIds());
					break;
				default:
					break;
				}
				if (sysAuthDao.exitByUserIdAndFuncId(record.getUserId(),record.getFuncId())) {
					sysAuthDao.update(record);
				} else{
					sysAuthDao.add(record);
				}
			}
			map.put(Constant.Status_Name.getValue()	, Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue()	, Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

}

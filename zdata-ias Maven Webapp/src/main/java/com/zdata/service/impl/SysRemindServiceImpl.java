package com.zdata.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdata.constant.Constant;
import com.zdata.dao.SysRemindDao;
import com.zdata.model.SysRemind;
import com.zdata.model.SysUser;
import com.zdata.service.SysRemindService;

@Service("sysRemindService")
public class SysRemindServiceImpl implements SysRemindService {

	@Autowired
	private SysRemindDao sysRemindDao;
	
	@Reference(check=false)
	private SysXtUserInfoService sysXtUserInfoService;
	
	@Override
	public Map<String, Object> findByAll(SysRemind record,SysUser curUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<SysRemind> rows = this.sysRemindDao.findByAll(record);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
			map.put(Constant.Rows_Name.getValue(), rows);
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> updateByUserId(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		//Map<String, Object> result = this.sysXtUserInfoService.findByUserId(userId);
		map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		return map;
	}

}

package com.zdata.ias.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.zdata.constant.Constant;
import com.zdata.dao.SysFileDao;

@Service("sysSysFileService")
public class SysSysFileServiceImpl implements SysSysFileService {

	@Resource
	private SysFileDao sysFileDao;
	
	@Override
	public Map<String, Object> deleteByBus(String busType, int busId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sysFileDao.deleteByBus(busType, busId);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

}

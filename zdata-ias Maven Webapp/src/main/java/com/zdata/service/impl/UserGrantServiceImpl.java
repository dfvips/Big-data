package com.zdata.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdata.constant.Constant;
import com.zdata.dao.UserGrantDao;
import com.zdata.model.UserGrant;
import com.zdata.service.UserGrantService;

@Service("userGrantService")
public class UserGrantServiceImpl implements UserGrantService{

	@Autowired
	private UserGrantDao userGrantDao;
	
	@Override
	public Map<String, Object> findByUserId(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<UserGrant> userGrantList = userGrantDao.findByUserId(userId);
			map.put(Constant.Rows_Name.getValue(), userGrantList);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

}

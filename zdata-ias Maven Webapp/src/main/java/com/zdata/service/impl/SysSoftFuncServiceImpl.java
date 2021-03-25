package com.zdata.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdata.constant.Constant;
import com.zdata.dao.SysSoftFuncDao;
import com.zdata.model.SysSoftFunc;
import com.zdata.service.SysSoftFuncService;

@Service("sysSoftFuncService")
public class SysSoftFuncServiceImpl implements SysSoftFuncService {

	@Autowired
	private SysSoftFuncDao sysSoftFuncDao;
	
	@Override
	public Map<String, Object> findBySoftId(Integer softId) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			List<SysSoftFunc> list = sysSoftFuncDao.findBySoftId(softId);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
			map.put(Constant.Rows_Name.getValue(), list);
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> saveBySoftId(Integer softId, String[] funcIds) {
		Map<String,Object> map = new HashMap<String, Object>();
		//删除软件预设权限
		try {
			sysSoftFuncDao.deleteBySoftId(softId);
			for (int i = 0; i < funcIds.length; i++) {
				SysSoftFunc softFunc = new SysSoftFunc();
				softFunc.setSoftId(softId);
				softFunc.setFuncId(funcIds[i]);
				sysSoftFuncDao.add(softFunc);
			}
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

}

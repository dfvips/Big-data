package com.zdata.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdata.constant.Constant;
import com.zdata.dao.SysSoftDao;
import com.zdata.model.SysSoft;
import com.zdata.service.SysSoftService;

@Service("sysSoftService")
public class SysSoftServiceImpl implements SysSoftService {

	@Resource
	private SysSoftDao sysSoftDao;
	
	@Override
	public SysSoft findBySoftId(String softId) {
		return sysSoftDao.findBySoftId(softId);
	}

	@Override
	public Map<String, Object> findByAll(SysSoft record) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			record.setPage((record.getPage()-1)*record.getRows());
			List<SysSoft> rows = sysSoftDao.findByAll(record);
			int total = sysSoftDao.findCountByAll(record);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
			map.put(Constant.Rows_Name.getValue(), rows);
			map.put(Constant.Total_Name.getValue(), total);
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> save(SysSoft record) {
		/*Map<String, Object> map = new HashMap<String, Object>();
		if(record.getId()==null){
			
		}*/
		return null;
	}

	@Override
	public Map<String, Object> findAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<SysSoft> rows = sysSoftDao.findAllList(null);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
			map.put(Constant.Rows_Name.getValue(), rows);
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

}

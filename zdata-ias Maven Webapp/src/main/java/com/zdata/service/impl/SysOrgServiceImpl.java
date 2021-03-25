package com.zdata.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdata.constant.Constant;
import com.zdata.dao.SysOrgDao;
import com.zdata.model.SysOrg;
import com.zdata.model.SysUser;
import com.zdata.service.SysOrgService;
import com.zdata.util.SessionUtils;
import com.zdata.util.StringUtil;

/**
 * 
* <p>Title: OrgServiceImpl</p>  
* <p>Description: </p>  
* @author 梦丶随心飞
* @date 2019年6月9日
 */
@Service("sysOrgService")
public class SysOrgServiceImpl implements SysOrgService {

	@Resource
	private SysOrgDao sysOrgDao;
	
	@Override
	public boolean isParent(String orgId) {
		return sysOrgDao.isParent(orgId);
	}

	@Override
	public Map<String, Object> findByPid(String orgId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<SysOrg> rows = this.sysOrgDao.findByPid(orgId);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
			map.put(Constant.Rows_Name.getValue(), rows);
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public List<SysOrg> findByPidAndIsShow(String orgId, String isShow) {
		return sysOrgDao.findByPidAndIsShow(orgId, isShow);
	}
	
	@Override
	public void add(SysOrg org) {
		sysOrgDao.add(org);		
	}

	@Override
	public void update(SysOrg org) {
		sysOrgDao.update(org);		
	}

	@Override
	public void delete(String id) {
		sysOrgDao.delete(id);
	}

	@Override
	public void setColumn(String columnName, String columnValue, String id) {
		sysOrgDao.setColumn(columnName, columnValue, id);		
	}

	@Override
	public SysOrg loadBySoftIdAndUserId(String softId, String userId) {
		return sysOrgDao.loadBySoftIdAndUserId(softId, userId);
	}

	@Override
	public Map<String, Object> loadByOrgId(String orgId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysOrg row = this.sysOrgDao.findByOrgId(orgId);
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
			map.put(Constant.Row_Name.getValue(),row);
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> save(SysOrg org,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(StringUtil.isNotEmpty(org.getOrgId())){
				this.sysOrgDao.update(org);
			}else{
				SysUser user = SessionUtils.getCurrentUser(request);
				org.setExchangDate(new Date());
				org.setUserId(user.getUserId());
				//设置单前节点。
				List<SysOrg> children = sysOrgDao.findByPid(org.getOrgParentId());
				//没有存在孩子节点
				if (children.size()==0) {
					org.setOrgId(org.getOrgParentId()+"001");
				}else{
					//获取倒序排序后的第一个
					Integer length = children.get(children.size()-1).getOrgId().length();
					String last = children.get(children.size()-1).getOrgId().substring(length-3, length);
					Integer index = Integer.parseInt(last)+1;
					org.setOrgId(org.getOrgParentId()+String.format("%03d", index));
				}
				this.sysOrgDao.add(org);
			}
			map.put(Constant.Status_Name.getValue(), Constant.SUCCESS.getValue());
		} catch (Exception e) {
			map.put(Constant.Status_Name.getValue(), Constant.FAIL.getValue());
			e.printStackTrace();
		}
		return map;
	}

}

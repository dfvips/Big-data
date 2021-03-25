package com.zdata.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zdata.dao.SysOrgDao;
import com.zdata.model.SysOrg;
/**
 * 
* <p>Title: OrgDaoImpl</p>  
* <p>Description: </p>  
* @author 梦丶随心飞
* @date 2019年6月9日
 */
@Repository("sysOrgDao")
public class SysOrgDaoImpl implements SysOrgDao{
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
    public int add(SysOrg sysOrg) {
        return jdbcTemplate.update("insert into sys_org  (org_id,org_name,org_parent_id,org_sort,position,card_id,address,user_id,is_show,exchang_date ) values (?,?,?,?,?,?,?,?,?,? )",
        sysOrg.getOrgId(),sysOrg.getOrgName(),sysOrg.getOrgParentId(),sysOrg.getOrgSort(),sysOrg.getPosition(),sysOrg.getCardId(),sysOrg.getAddress(),sysOrg.getUserId(),sysOrg.getIsShow(),sysOrg.getExchangDate());
    }

    @Override
    public int update(SysOrg sysOrg) {
        return jdbcTemplate.update("update  sys_org  SET org_name=?,org_parent_id=?,org_sort=?,position=?,card_id=?,address=?,user_id=?,is_show=?,exchang_date=?"
        +" where org_id=?",
            sysOrg.getOrgName(),sysOrg.getOrgParentId(),sysOrg.getOrgSort(),sysOrg.getPosition(),sysOrg.getCardId(),sysOrg.getAddress(),sysOrg.getUserId(),sysOrg.getIsShow(),sysOrg.getExchangDate(),
            sysOrg.getOrgId())
        ;
    }

    @Override
    public int delete(String orgId) {
        return jdbcTemplate.update("delete from sys_org where org_id=?",orgId);
    }

    @Override
    public SysOrg findByOrgId(String orgId) {
        List<SysOrg> list = jdbcTemplate.query("select * from sys_org where org_id=?", new Object[]{orgId}, new BeanPropertyRowMapper<SysOrg>(SysOrg.class));
        if(list!=null && list.size()>0){
            SysOrg sysOrg = list.get(0);
            return sysOrg;
        }else{
             return null;
        }
    }

    @Override
    public List<SysOrg> findAllList(Map<String,Object> params) {
        List<SysOrg> list = jdbcTemplate.query("select * from sys_org", new Object[]{}, new BeanPropertyRowMapper<SysOrg>(SysOrg.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }
	
	@Override
	public boolean isParent(String orgId) {
		String sql = "select count(org_id) from sys_org where org_parent_id=?";
		int result = jdbcTemplate.queryForObject(sql, new Object[] { orgId },Integer.class);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<SysOrg> findByPid(String orgId) {
		List<SysOrg> list = jdbcTemplate.query("select * from sys_org where org_parent_id=? order by org_id asc", new Object[]{orgId}, new BeanPropertyRowMapper<SysOrg>(SysOrg.class));
		return pageTree(list);
	}
	
	@Override
	public List<SysOrg> findByPidAndIsShow(String orgId, String isShow) {
		List<SysOrg> list = jdbcTemplate.query("select * from sys_org where org_parent_id=? and is_show=? order by org_id asc", new Object[]{orgId,isShow}, new BeanPropertyRowMapper<SysOrg>(SysOrg.class));
		return pageTree(list);
	}

	@Override
	public List<SysOrg> pageTree(List<SysOrg> orgList){
		List<SysOrg> rtn = new ArrayList<SysOrg>();
		for(SysOrg item : orgList){
			if (isParent(item.getOrgId())) {//判断是否是父节点
				item.setIsParent(1);
			} else {
				item.setIsParent(0);
			}
			rtn.add(item);
		}
		return rtn;
	}

	@Override
	public void setColumn(String columnName, String columnValue, String id) {
		String sql = "update sys_org set "+columnName+"=? where org_id=?";
		jdbcTemplate.update(sql,new Object[] { columnValue,id });
	}

	@Override
	public SysOrg loadBySoftIdAndUserId(String softId, String userId) {
		String sql = "select * from sys_org where org_id like ? and user_id=? order by org_parent_id asc limit 1";
		List<SysOrg> list = jdbcTemplate.query(sql, new Object[]{softId+"%",userId}, new BeanPropertyRowMapper<SysOrg>(SysOrg.class));
        if(list!=null && list.size()>0){
            SysOrg sysOrg = list.get(0);
            return sysOrg;
        }else{
             return null;
        }
	}

	
}

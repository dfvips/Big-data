package com.zdata.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zdata.dao.SysRoleDao;
import com.zdata.model.SysRole;
import com.zdata.util.StringUtil;

@Repository("sysRoleDao")
public class SysRoleDaoImpl implements SysRoleDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void add(SysRole sysRole) {
		String sql ="insert into sys_role (role_id,role_name,soft_id) values (?,?,?)";
		jdbcTemplate.update(sql,new Object[] {sysRole.getRoleId(),sysRole.getRoleName(),sysRole.getSoftId()});
	}

	@Override
	public void update(SysRole sysRole) {
		String sql ="update sys_role set role_id=?,role_name=?,soft_id=? where id=?";
		jdbcTemplate.update(sql,new Object[] {sysRole.getRoleId(),sysRole.getRoleName(),sysRole.getSoftId(),sysRole.getId()});
	}

	@Override
	public List<SysRole> findByAll(SysRole record) {
		StringBuffer sb = new StringBuffer("select * from sys_role where 1=1");
		if (record != null) {
			if (record.getSoftId()!=null) {
				sb.append(" and soft_id="+record.getSoftId());
			}
			if(StringUtil.isNotEmpty(record.getRoleId())){
				sb.append(" and role_id='"+record.getRoleId()+"'");
			}
			if(StringUtil.isNotEmpty(record.getRoleName())){
				sb.append(" and role_name='"+record.getRoleName()+"'");
			}
		}
		sb.append(" limit "+record.getPage()+","+record.getRows());
		final List<SysRole> sysRoleList = new ArrayList<SysRole>();
		jdbcTemplate.query(sb.toString(),
				new Object[] { }, new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						SysRole sysRole = new SysRole();
						sysRole.setId(rs.getInt("id"));
						sysRole.setSoftId(rs.getInt("soft_id"));
						sysRole.setRoleId(rs.getString("role_id"));
						sysRole.setRoleName(rs.getString("role_name"));
						sysRoleList.add(sysRole);
					}
		});
		return sysRoleList;
	}

	@Override
	public int findCountByAll(SysRole record) {
		StringBuffer sb = new StringBuffer("select count(1) from sys_role where 1=1");
		if (record != null) {
			if (record.getSoftId()!=null) {
				sb.append(" and soft_id="+record.getSoftId());
			}
			if(StringUtil.isNotEmpty(record.getRoleId())){
				sb.append(" and role_id='"+record.getRoleId()+"'");
			}
			if(StringUtil.isNotEmpty(record.getRoleName())){
				sb.append(" and role_name='"+record.getRoleName()+"'");
			}
		}
		return jdbcTemplate.queryForObject(sb.toString(), Integer.class);
	}

	@Override
	public void delete(Integer id) {
		String sql = "delete from sys_role where id=?";
		jdbcTemplate.update(sql, new Object[] { id });
	}

	@Override
	public SysRole loadById(Integer id) {
		String sql = "select * from sys_role where id=?";
		final SysRole sysRole = new SysRole();
		jdbcTemplate.query(sql, new Object[] { id }, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				sysRole.setId(rs.getInt("id"));
				sysRole.setRoleId(rs.getString("role_id"));
				sysRole.setRoleName(rs.getString("role_name"));
				sysRole.setSoftId(rs.getInt("soft_id"));
			}
		});
		return sysRole;
	}

}

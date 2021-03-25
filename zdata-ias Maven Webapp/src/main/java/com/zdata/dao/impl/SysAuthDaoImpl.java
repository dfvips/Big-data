package com.zdata.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.zdata.dao.SysAuthDao;
import com.zdata.model.SysAuth;

@Repository("sysAuthDao")
public class SysAuthDaoImpl implements SysAuthDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public SysAuth loadByUserId(String userId, String funcId) {
		String sql = "select * from sys_auth where user_id=? and func_id=?";
		final SysAuth sysAuth = new SysAuth();
		jdbcTemplate.query(sql, new Object[] { userId,funcId }, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				sysAuth.setId(rs.getInt("id"));
				sysAuth.setUserId(rs.getString("user_id"));
				sysAuth.setFuncId(rs.getString("func_id"));
				sysAuth.setOrgId(rs.getString("org_id"));
				sysAuth.setAuthType(rs.getInt("auth_type"));
			}
		});
		return sysAuth;
	}

	@Override
	public void add(SysAuth sysAuth) {
		String sql = "insert into sys_auth (user_id,func_id,org_id,auth_type,auth_user_ids) values (?,?,?,?,?)";
		jdbcTemplate.update(sql,new Object[] { sysAuth.getUserId(),sysAuth.getFuncId(),sysAuth.getOrgId(),sysAuth.getAuthType(),sysAuth.getAuthUserIds() });
	}

	@Override
	public void update(SysAuth sysAuth) {
		String sql = "update sys_auth set org_id=?,auth_type=?,auth_user_ids=? where user_id=? and func_id=?";
		jdbcTemplate.update(sql,new Object[] { sysAuth.getOrgId(),sysAuth.getAuthType(),sysAuth.getAuthUserIds(),sysAuth.getUserId(),sysAuth.getFuncId() });
	}

	@Override
	public boolean exitByUserIdAndFuncId(String userId,String funcId) {
		String sql = "select count(1) from sys_auth where user_id=? and func_id=?";
		int result = jdbcTemplate.queryForObject(sql,new Object[] { userId,funcId }, Integer.class);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

}

package com.zdata.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zdata.dao.SysRoleInfoDao;
import com.zdata.model.SysRoleInfo;

@Repository("sysRoleInfoDao")
public class SysRoleInfoDaoImpl implements SysRoleInfoDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<SysRoleInfo> findByRoleId(Integer roleId,Integer page,Integer rows) {
		StringBuffer sb = new StringBuffer("select * from sys_role_info as ri left join sys_role as r on ri.role_id=r.id "
				+ "left join sys_user as u on ri.user_id=u.user_id where 1=1 and ri.role_id="+roleId);
		sb.append(" limit "+page+","+rows);
		final List<SysRoleInfo> sysRoleInfoList = new ArrayList<SysRoleInfo>();
		jdbcTemplate.query(sb.toString(),
				new Object[] { }, new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						SysRoleInfo sysRoleInfo = new SysRoleInfo();
						sysRoleInfo.setId(rs.getInt("ri.id"));
						sysRoleInfo.setRoleId(rs.getInt("ri.role_id"));
						sysRoleInfo.setUserId(rs.getString("ri.user_id"));
						sysRoleInfo.setUserName(rs.getString("u.user_name"));
						sysRoleInfoList.add(sysRoleInfo);
					}
		});
		return sysRoleInfoList;
	}

	@Override
	public void delete(Integer[] ids) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			sb.append(ids[i].toString()+",");
		}
		String sql = "delete from sys_role_info where id in ("+sb.substring(0, sb.length()-1)+")";
		jdbcTemplate.update(sql, new Object[] {  });
	}

	@Override
	public void deleteByRoleId(Integer roleId) {
		String sql = "delete from sys_role_info where role_id=?";
		jdbcTemplate.update(sql, new Object[] { roleId });
	}

	@Override
	public boolean exitByUser(Integer roleId, String userId) {
		String sql = "select count(1) from sys_role_info where role_id=? and user_id=?";
		int result = jdbcTemplate.queryForObject(sql, new Object[] { roleId,userId },Integer.class);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void add(SysRoleInfo sysRoleInfo) {
		String sql ="insert into sys_role_info (role_id,user_id) values (?,?)";
		jdbcTemplate.update(sql,new Object[] {sysRoleInfo.getRoleId(),sysRoleInfo.getUserId()});
	}

	@Override
	public List<SysRoleInfo> findByUserId(String userId) {
		StringBuffer sb = new StringBuffer("select * from sys_role_info where user_id=?");
		final List<SysRoleInfo> sysRoleInfoList = new ArrayList<SysRoleInfo>();
		jdbcTemplate.query(sb.toString(),
				new Object[] { userId }, new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						SysRoleInfo sysRoleInfo = new SysRoleInfo();
						sysRoleInfo.setId(rs.getInt("id"));
						sysRoleInfo.setRoleId(rs.getInt("role_id"));
						sysRoleInfo.setUserId(rs.getString("user_id"));
						sysRoleInfoList.add(sysRoleInfo);
					}
		});
		return sysRoleInfoList;
	}

	@Override
	public void deleteByUserId(String userId) {
		String sql = "delete from sys_role_info where user_id=?";
		jdbcTemplate.update(sql, new Object[] { userId });
	}

}

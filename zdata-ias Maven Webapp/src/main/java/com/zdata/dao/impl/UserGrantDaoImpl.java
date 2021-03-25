package com.zdata.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zdata.dao.UserGrantDao;
import com.zdata.model.UserGrant;

@Repository("userGrantDao")
public class UserGrantDaoImpl implements UserGrantDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<UserGrant> findByUserId(String userId) {
		StringBuffer sb = new StringBuffer("select * from iassysusergrant where 1=1 and SYSUSERID='"+userId+"' ORDER BY SYSGRANTID asc");
		final List<UserGrant> userGrantList = new ArrayList<UserGrant>();
		jdbcTemplate.query(sb.toString(),
				new Object[] {}, new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						UserGrant userGrant=new UserGrant();
						userGrant.setRowId(rs.getInt("ROWID"));
						userGrant.setUserId(rs.getString("SYSUSERID"));
						userGrant.setGrantId(rs.getString("SYSGRANTID"));
						userGrantList.add(userGrant);
					}
				});
		return userGrantList;
	}

	@Override
	public void deleteByUserId(String userId) {
		String sql = "delete from iassysusergrant where SYSUSERID='"+userId+"'";
		jdbcTemplate.update(sql, new Object[] { });
	}

	@Override
	public void add(UserGrant userGrant) {
		String sql ="insert into iassysusergrant (SYSUSERID,SYSGRANTID) values (?,?)";
		jdbcTemplate.update(sql,new Object[] {userGrant.getUserId(),userGrant.getGrantId()});
	}

}

package com.zdata.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zdata.dao.UserGroupDao;
import com.zdata.model.PageBean;
import com.zdata.model.User;
import com.zdata.model.UserGroup;
import com.zdata.util.DateUtil;
import com.zdata.util.StringUtil;

@Repository("userGroupDao")
public class UserGroupDaoImpl implements UserGroupDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<UserGroup> findByOrgId(PageBean pageBean, User s_User,
			String orgId) {
		StringBuffer sb = new StringBuffer("select * from iassysusergroup as ug left join iassysuser as u on ug.SYSUSERID=u.SYSUSERID left join iassysorg as o on ug.SYSUSERGROUPID=o.SYSORGID");
		if (StringUtil.isNotEmpty(orgId)) {
			sb.append(" and ug.SYSUSERGROUPID='"+orgId+"'");
		}
		if (s_User!=null) {
			if (StringUtil.isNotEmpty(s_User.getSysUserId())) {
				sb.append(" and u.SYSUSERID like '%"+s_User.getSysUserId()+"%'");
			}
			if (StringUtil.isNotEmpty(s_User.getSysUserName())) {
				sb.append(" and u.SYSUSERNAME like '%"+s_User.getSysUserName()+"%'");
			}
		}
		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + ","
					+ pageBean.getPageSize());
		}
		final List<UserGroup> userGroupList = new ArrayList<UserGroup>();
		jdbcTemplate.query(sb.toString().replaceFirst("and", "where"),
				new Object[] {}, new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						UserGroup userGroup=new UserGroup();
						userGroup.setRowId(rs.getInt("ug.ROWID"));
						userGroup.setSysUserId(rs.getString("ug.SYSUSERID"));
						userGroup.setSysUserName(rs.getString("u.SYSUSERNAME"));
						userGroup.setSysUserGroupId(rs.getString("ug.SYSUSERGROUPID"));
						userGroup.setSysUserGroupName(rs.getString("o.SYSORGNAME"));
						userGroup.setSysUserPosition(rs.getString("ug.SYSUSERPOSITION"));
						userGroup.setPositionOrder(rs.getString("ug.POSITIONORDER"));
						userGroup.setExchangDate(DateUtil.formatString(
								rs.getString("ug.ExchangDate"), "yyyy-MM-dd HH:mm:ss"));
						userGroupList.add(userGroup);
					}
				});
		return userGroupList;
	}

	@Override
	public int countByOrgId(User s_User, String orgId) {
		StringBuffer sb = new StringBuffer("select count(1) from iassysusergroup as ug left join iassysuser as u on ug.SYSUSERID=u.SYSUSERID left join iassysorg as o on ug.SYSUSERGROUPID=o.SYSORGID");
		if (StringUtil.isNotEmpty(orgId)) {
			sb.append(" and ug.SYSUSERGROUPID='"+orgId+"'");
		}
		if (s_User!=null) {
			if (StringUtil.isNotEmpty(s_User.getSysUserId())) {
				sb.append(" and u.SYSUSERID like '%"+s_User.getSysUserId()+"%'");
			}
			if (StringUtil.isNotEmpty(s_User.getSysUserName())) {
				sb.append(" and u.SYSUSERNAME like '%"+s_User.getSysUserName()+"%'");
			}
		}
		return jdbcTemplate.queryForObject(sb.toString().replaceFirst("and", "where"), Integer.class);
	}

	@Override
	public void add(UserGroup userGroup) {
		String sql = "insert into iassysusergroup (SYSUSERID,SYSUSERGROUPID,SYSUSERPOSITION,POSITIONORDER,ExchangDate) values (?,?,?,?,?)";
		jdbcTemplate.update(sql,new Object[] { userGroup.getSysUserId(),userGroup.getSysUserGroupId(),userGroup.getSysUserPosition(),userGroup.getPositionOrder(),userGroup.getExchangDate() });
	}

	@Override
	public void delete(int rowId) {
		String sql = "delete from iassysusergroup where ROWID=?";
		jdbcTemplate.update(sql, new Object[] { rowId });
	}

	@Override
	public boolean existByOrgIdAndUserId(String orgId, String userId) {
		String sql = "select count(1) from iassysusergroup where SYSUSERGROUPID=? and SYSUSERID=?";
		int result = jdbcTemplate.queryForObject(sql,new Object[] { orgId,userId }, Integer.class);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

}

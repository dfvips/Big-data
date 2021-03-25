package com.zdata.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zdata.dao.UserDao;
import com.zdata.model.PageBean;
import com.zdata.model.User;
import com.zdata.util.DateUtil;
import com.zdata.util.StringUtil;

/**
 * 用户事务实现接口
 * 
 * @author 梦丶随心飞
 *
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<User> findBySoftId(PageBean pageBean,
			User searchUser, String softId, String state) {
		StringBuffer sb = new StringBuffer("select * from iassysuser");
		if(StringUtil.isNotEmpty(softId)){
			sb.append(" and SYSSOFTID='" + softId+"'");
			if(StringUtil.isNotEmpty(state)){
				sb.append(" and State='" + state+"'");
				if(searchUser!=null){
					if(StringUtil.isNotEmpty(searchUser.getSysUserId())){
						sb.append(" and SYSUSERID like '%" + searchUser.getSysUserId()+"%'");
					}
					if(StringUtil.isNotEmpty(searchUser.getSysUserName())){
						sb.append(" and SYSUSERNAME like '%" + searchUser.getSysUserName()+"%'");
					}
				}
			}
		}else{
			sb.append(" and SYSSOFTID='0'");
		}
		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + ","
					+ pageBean.getPageSize());
		}
		final List<User> iassysusersList = new ArrayList<User>();
		jdbcTemplate.query(sb.toString().replaceFirst("and", "where"),
				new Object[] {}, new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						User iassysuser=new User();
						iassysuser.setRowId(rs.getInt("ROWID"));
						iassysuser.setSysUserId(rs.getString("SYSUSERID"));
						iassysuser.setSysUserName(rs.getString("SYSUSERNAME"));
						//iassysuser.setSysUserPassword(rs.getString("SYSUSERPASSWORD"));
						iassysuser.setSysUserGroupId(rs.getString("SYSUSERGROUPID"));
						iassysuser.setSysSoftId(rs.getString("SYSSOFTID"));
						iassysuser.setSysSoftMainf(rs.getString("SYSSOFTMAINF"));
						iassysuser.setEmail(rs.getString("EMAIL"));
						iassysuser.setMobtele(rs.getString("MOBTELE"));
						iassysuser.setState(rs.getString("State"));
						iassysuser.setExchangDate(DateUtil.formatString(
								rs.getString("ExchangDate"), "yyyy-MM-dd HH:mm:ss"));
						iassysusersList.add(iassysuser);
					}
				});
		return iassysusersList;
	}

	@Override
	public int countBySoftId(User searchUser, String softId,
			String state) {
		StringBuffer sb = new StringBuffer("select count(*) from iassysuser");
		if(StringUtil.isNotEmpty(softId)){
			sb.append(" and SYSSOFTID='" + softId+"'");
			if(StringUtil.isNotEmpty(state)){
				sb.append(" and State='" + state+"'");
				if(searchUser!=null){
					if(StringUtil.isNotEmpty(searchUser.getSysUserId())){
						sb.append(" and SYSUSERID like '%" + searchUser.getSysUserId()+"%'");
					}
					if(StringUtil.isNotEmpty(searchUser.getSysUserName())){
						sb.append(" and SYSUSERNAME like '%" + searchUser.getSysUserName()+"%'");
					}
				}
			}
		}else{
			sb.append(" and SYSSOFTID='0'");
		}
		return jdbcTemplate.queryForObject(sb.toString().replaceFirst("and", "where"), Integer.class);
	}

	@Override
	public User loadByrowId(int rowId) {
		String sql = "select * from iassysuser where ROWID=?";
		final User user = new User();
		jdbcTemplate.query(sql, new Object[] { rowId }, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				user.setRowId(rs.getInt("ROWID"));
				user.setSysUserId(rs.getString("SYSUSERID"));
				user.setSysUserName(rs.getString("SYSUSERNAME"));
				user.setSysUserPassword(rs.getString("SYSUSERPASSWORD"));
				user.setSysUserGroupId(rs.getString("SYSUSERGROUPID"));
				user.setSysSoftId(rs.getString("SYSSOFTID"));
				user.setSysSoftMainf(rs.getString("SYSSOFTMAINF"));
				user.setEmail(rs.getString("EMAIL"));
				user.setMobtele(rs.getString("MOBTELE"));
				user.setState(rs.getString("State"));
				user.setExchangDate(DateUtil.formatString(
						rs.getString("ExchangDate"), "yyyy-MM-dd HH:mm:ss"));
				user.setCreate(rs.getString("createName"));
			}
		});
		return user;
	}

	@Override
	public void add(User user) {
		String sql = "insert into iassysuser (SYSUSERID,SYSUSERNAME,SYSUSERPASSWORD,SYSUSERGROUPID,SYSSOFTID,SYSSOFTMAINF,EMAIL,MOBTELE,State,ExchangDate,createName) values (?,?,?,?,?,?,?,?,?,now(),?)";
		jdbcTemplate.update(sql,new Object[] { user.getSysUserId(),user.getSysUserName(),user.getSysUserPassword(),user.getSysUserGroupId(),user.getSysSoftId(),user.getSysSoftMainf(),user.getEmail(),user.getMobtele(),user.getState(),user.getCreate() });
	}

	@Override
	public List<User> findByOrgId(PageBean pageBean, String search, String orgId,boolean isLike) {
		StringBuffer sb = new StringBuffer("select * from iassysuser as u left join iassysorg as o on u.SYSUSERGROUPID=o.SYSORGID");
		if (StringUtil.isNotEmpty(orgId)) {
			if(isLike){
				sb.append(" and u.SYSUSERGROUPID like '" + orgId+"%'");
			}else {
				sb.append(" and u.SYSUSERGROUPID='" + orgId+"'");
			}
			if (StringUtil.isNotEmpty(search)) {
				sb.append(" and (u.SYSUSERID like '%"+search+"%' or u.SYSUSERNAME like '%"+search+"%' or u.EMAIL like '%"+search+"%' or u.MOBTELE like '%"+search+"%')");
			}
		}
		sb.append(" order by u.SYSUSERID desc");
		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + ","
					+ pageBean.getPageSize());
		}
		final List<User> userList = new ArrayList<User>();
		jdbcTemplate.query(sb.toString().replaceFirst("and", "where"),
				new Object[] {}, new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						User user=new User();
						user.setRowId(rs.getInt("u.ROWID"));
						user.setSysUserId(rs.getString("u.SYSUSERID"));
						user.setSysUserName(rs.getString("u.SYSUSERNAME"));
						user.setSysUserGroupId(rs.getString("u.SYSUSERGROUPID"));
						user.setSysUserGroupName(rs.getString("o.SYSORGNAME"));
						user.setSysSoftId(rs.getString("u.SYSSOFTID"));
						user.setEmail(rs.getString("u.EMAIL"));
						user.setMobtele(rs.getString("u.MOBTELE"));
						user.setState(rs.getString("u.State"));
						user.setExchangDate(DateUtil.formatString(
								rs.getString("u.ExchangDate"), "yyyy-MM-dd HH:mm:ss"));
						user.setCreate(rs.getString("u.createName"));
						userList.add(user);
					}
				});
		return userList;
	}

	@Override
	public int countByOrgId(String search, String orgId,boolean isLike) {
		StringBuffer sb = new StringBuffer("select count(*) from iassysuser as u left join iassysorg as o on u.SYSUSERGROUPID=o.SYSORGID");
		if (StringUtil.isNotEmpty(orgId)) {
			if(isLike){
				sb.append(" and u.SYSUSERGROUPID like '" + orgId+"%'");
			}else {
				sb.append(" and u.SYSUSERGROUPID='" + orgId+"'");
			}
			if (StringUtil.isNotEmpty(search)) {
				sb.append(" and (u.SYSUSERID like '%"+search+"%' or u.SYSUSERNAME like '%"+search+"%' or u.EMAIL like '%"+search+"%' or u.MOBTELE like '%"+search+"%')");
			}
		}
		return jdbcTemplate.queryForObject(sb.toString().replaceFirst("and", "where"), Integer.class);
	}

	@Override
	public User loadByUserId(String userId) {
		String sql = "select * from iassysuser as u left join iassysorg as o on u.SYSUSERGROUPID=o.SYSORGID where SYSUSERID=?";
		final User user = new User();
		jdbcTemplate.query(sql, new Object[] { userId }, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				user.setRowId(rs.getInt("u.ROWID"));
				user.setSysUserId(rs.getString("u.SYSUSERID"));
				user.setSysUserName(rs.getString("u.SYSUSERNAME"));
				user.setSysUserPassword(rs.getString("u.SYSUSERPASSWORD"));
				user.setSysUserGroupId(rs.getString("u.SYSUSERGROUPID"));
				user.setSysUserGroupName(rs.getString("o.SYSORGNAME"));
				user.setSysSoftId(rs.getString("u.SYSSOFTID"));
				user.setSysSoftMainf(rs.getString("u.SYSSOFTMAINF"));
				user.setEmail(rs.getString("u.EMAIL"));
				user.setMobtele(rs.getString("u.MOBTELE"));
				user.setState(rs.getString("u.State"));
				user.setExchangDate(DateUtil.formatString(
						rs.getString("u.ExchangDate"), "yyyy-MM-dd HH:mm:ss"));
				user.setCreate(rs.getString("u.createName"));
			}
		});
		return user;
	}

	@Override
	public boolean existUserByOrgId(String orgId) {
		String sql = "select count(*) from iassysuser where SYSUSERGROUPID=?";
		int result = jdbcTemplate.queryForObject(sql,new Object[] { orgId }, Integer.class);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void saveColumn(String columnName, String columnValue, int id) {
		String sql = "update iassysuser set "+columnName+"='"+columnValue+"' where ROWID='"+id+"'";
		jdbcTemplate.update(sql,new Object[] {});
	}

	@Override
	public List<User> findByMobtele(String mobTele) {
		String sql = "select * from iassysuser where MOBTELE=?";
		final List<User> userList = new ArrayList<User>();
		jdbcTemplate.query(sql, new Object[] { mobTele }, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				User user = new User();
				user.setRowId(rs.getInt("ROWID"));
				user.setSysUserId(rs.getString("SYSUSERID"));
				user.setSysUserName(rs.getString("SYSUSERNAME"));
				user.setSysUserPassword(rs.getString("SYSUSERPASSWORD"));
				user.setSysUserGroupId(rs.getString("SYSUSERGROUPID"));
				user.setSysSoftId(rs.getString("SYSSOFTID"));
				user.setSysSoftMainf(rs.getString("SYSSOFTMAINF"));
				user.setEmail(rs.getString("EMAIL"));
				user.setMobtele(rs.getString("MOBTELE"));
				user.setState(rs.getString("State"));
				user.setExchangDate(DateUtil.formatString(
						rs.getString("ExchangDate"), "yyyy-MM-dd HH:mm:ss"));
				user.setCreate(rs.getString("createName"));
				userList.add(user);
			}
		});
		return userList;
	}

	@Override
	public void update(User user) {
		String sql = "update iassysuser set SYSUSERID=?,SYSUSERNAME=?,SYSUSERPASSWORD=?,SYSUSERGROUPID=?,SYSSOFTID=?,SYSSOFTMAINF=?,EMAIL=?,MOBTELE=?,State=?,ExchangDate=now(),createName=? where ROWID=?";
		jdbcTemplate.update(sql,new Object[] { user.getSysUserId(),user.getSysUserName(),user.getSysUserPassword(),user.getSysUserGroupId(),user.getSysSoftId(),user.getSysSoftMainf(),user.getEmail(),user.getMobtele(),user.getState(),user.getCreate(),user.getRowId() });
	}

	@Override
	public boolean existUserByUserId(String userId, Integer rowId) {
		StringBuffer sb = new StringBuffer("select count(1) from iassysuser where SYSUSERID='"+userId+"'");
		if (rowId!=null) {
			sb.append(" and ROWID != " + rowId);
		}
		int result = jdbcTemplate.queryForObject(sb.toString(),new Object[] { }, Integer.class);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<User> findAccounts(String softId) {
		String sql = "select * from iassysuser where SYSSOFTID=? order by SYSUSERID desc";
		final List<User> userList = new ArrayList<User>();
		jdbcTemplate.query(sql,
				new Object[] {softId}, new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						User user=new User();
						user.setRowId(rs.getInt("ROWID"));
						user.setSysUserId(rs.getString("SYSUSERID"));
						user.setSysUserName(rs.getString("SYSUSERNAME"));
						user.setSysUserGroupId(rs.getString("SYSUSERGROUPID"));
						user.setSysSoftId(rs.getString("SYSSOFTID"));
						user.setEmail(rs.getString("EMAIL"));
						user.setMobtele(rs.getString("MOBTELE"));
						user.setState(rs.getString("State"));
						user.setExchangDate(DateUtil.formatString(
								rs.getString("ExchangDate"), "yyyy-MM-dd HH:mm:ss"));
						user.setCreate(rs.getString("createName"));
						userList.add(user);
					}
				});
		return userList;
	}

}

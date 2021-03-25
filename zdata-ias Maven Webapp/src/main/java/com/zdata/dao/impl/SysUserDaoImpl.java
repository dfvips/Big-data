package com.zdata.dao.impl;

import java.util.List;
import java.util.Map;

import com.zdata.dao.SysUserDao;
import com.zdata.model.PageBean;
import com.zdata.model.SysUser;
import com.zdata.util.StringUtil;

@Repository("sysUserDao")
public class SysUserDaoImpl implements SysUserDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(SysUser sysUser) {
        return jdbcTemplate.update("insert into sys_user  (id,user_id,user_name,user_password,user_group_id,soft_id,soft_main,email,mobtele,state,exchang_date,create_name,flow_password,role_id ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,? )",
        sysUser.getId(),sysUser.getUserId(),sysUser.getUserName(),sysUser.getUserPassword(),sysUser.getUserGroupId(),sysUser.getSoftId(),sysUser.getSoftMain(),sysUser.getEmail(),sysUser.getMobtele(),sysUser.getState(),sysUser.getExchangDate(),sysUser.getCreateName(),sysUser.getFlowPassword(),sysUser.getRoleId());
    }

    @Override
    public int update(SysUser sysUser) {
        return jdbcTemplate.update("UPDATE  sys_user  SET user_id=?,user_name=?,user_password=?,user_group_id=?,soft_id=?,soft_main=?,email=?,mobtele=?,state=?,exchang_date=?,create_name=?,flow_password=?,role_id=?"
        +" where id=?",
            sysUser.getUserId(),sysUser.getUserName(),sysUser.getUserPassword(),sysUser.getUserGroupId(),sysUser.getSoftId(),sysUser.getSoftMain(),sysUser.getEmail(),sysUser.getMobtele(),sysUser.getState(),sysUser.getExchangDate(),sysUser.getCreateName(),sysUser.getFlowPassword(),sysUser.getRoleId(),
            sysUser.getId())
        ;
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("DELETE from sys_user where id=?",id);
    }

    @Override
    public SysUser findById(int id) {
        List<SysUser> list = jdbcTemplate.query("select * from sys_user where id=?", new Object[]{id}, new BeanPropertyRowMapper<SysUser>(SysUser.class));
        if(list!=null && list.size()>0){
            SysUser sysUser = list.get(0);
            return sysUser;
        }else{
             return null;
        }
    }

    @Override
    public List<SysUser> findAllList(Map<String,Object> params) {
        List<SysUser> list = jdbcTemplate.query("select * from sys_user", new Object[]{}, new BeanPropertyRowMapper<SysUser>(SysUser.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }

	@Override
	public List<SysUser> findByOrgId(PageBean pageBean, String search, String orgId, boolean isLike) {
		StringBuffer sb = new StringBuffer("select * from sys_user");
		if (StringUtil.isNotEmpty(orgId)) {
			if(isLike){
				sb.append(" and user_group_id like '" + orgId+"%'");
			}else {
				sb.append(" and user_group_id='" + orgId+"'");
			}
			if (StringUtil.isNotEmpty(search)) {
				sb.append(" and (user_id like '%"+search+"%' or user_name like '%"+search+"%' or email like '%"+search+"%' or mobtele like '%"+search+"%')");
			}
		}
		sb.append(" order by user_id desc");
		if (pageBean != null) {
			sb.append(" limit " + pageBean.getStart() + ","
					+ pageBean.getPageSize());
		}
		return jdbcTemplate.query(sb.toString().replaceFirst("and", "where"),new Object[] {}, new BeanPropertyRowMapper<SysUser>(SysUser.class));
	}

	@Override
	public int countByOrgId(String search, String orgId, boolean isLike) {
		StringBuffer sb = new StringBuffer("select count(1) from sys_user");
		if (StringUtil.isNotEmpty(orgId)) {
			if(isLike){
				sb.append(" and user_group_id like '" + orgId+"%'");
			}else {
				sb.append(" and user_group_id='" + orgId+"'");
			}
			if (StringUtil.isNotEmpty(search)) {
				sb.append(" and (user_id like '%"+search+"%' or user_name like '%"+search+"%' or email like '%"+search+"%' or mobtele like '%"+search+"%')");
			}
		}
		return jdbcTemplate.queryForObject(sb.toString().replaceFirst("and", "where"), Integer.class);
	}

	@Override
	public SysUser loadByUserId(String userId) {
		List<SysUser> list = jdbcTemplate.query("select * from sys_user where user_id=?", new Object[]{userId}, new BeanPropertyRowMapper<SysUser>(SysUser.class));
        if(list!=null && list.size()>0){
            SysUser sysUser = list.get(0);
            return sysUser;
        }else{
             return null;
        }
	}

	@Override
	public List<SysUser> findBySoftIdOrderByUserId(String softId) {
		String sql = "select * from sys_user where soft_id=? order by user_id desc";
		List<SysUser> list = jdbcTemplate.query(sql, new Object[]{softId}, new BeanPropertyRowMapper<SysUser>(SysUser.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
             return null;
        }
	}

	@Override
	public SysUser loadByMobtele(String mobtele) {
		List<SysUser> list = jdbcTemplate.query("select * from sys_user where mobtele=?", new Object[]{mobtele}, new BeanPropertyRowMapper<SysUser>(SysUser.class));
        if(list!=null && list.size()>0){
            SysUser sysUser = list.get(0);
            return sysUser;
        }else{
             return null;
        }
	}
	
}

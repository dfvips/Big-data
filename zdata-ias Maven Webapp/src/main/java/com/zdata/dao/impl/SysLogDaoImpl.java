package com.zdata.dao.impl;

import java.util.List;
import java.util.Map;

import com.zdata.dao.SysLogDao;
import com.zdata.model.PageBean;
import com.zdata.model.SysLog;
import com.zdata.util.StringUtil;

@Repository("sysLogDao")
public class SysLogDaoImpl implements SysLogDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(SysLog sysLog) {
        return jdbcTemplate.update("insert into sys_log  (id,user_id,user_name,soft_id,create_time,ip_address,params,request_address,type ) values (?,?,?,?,?,?,?,?,? )",
        sysLog.getId(),sysLog.getUserId(),sysLog.getUserName(),sysLog.getSoftId(),sysLog.getCreateTime(),sysLog.getIpAddress(),sysLog.getParams(),sysLog.getRequestAddress(),sysLog.getType());
    }

    @Override
    public int update(SysLog sysLog) {
        return jdbcTemplate.update("UPDATE  sys_log  SET user_id=?,user_name=?,soft_id=?,create_time=?,ip_address=?,params=?,request_address=?,type=?"
        +" where id=?",
            sysLog.getUserId(),sysLog.getUserName(),sysLog.getSoftId(),sysLog.getCreateTime(),sysLog.getIpAddress(),sysLog.getParams(),sysLog.getRequestAddress(),sysLog.getType(),
            sysLog.getId())
        ;
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("DELETE from sys_log where id=?",id);
    }

    @Override
    public SysLog findById(int id) {
        List<SysLog> list = jdbcTemplate.query("select * from sys_log where id=?", new Object[]{id}, new BeanPropertyRowMapper<SysLog>(SysLog.class));
        if(list!=null && list.size()>0){
            SysLog sysLog = list.get(0);
            return sysLog;
        }else{
             return null;
        }
    }

    @Override
    public List<SysLog> findAllList(Map<String,Object> params) {
        List<SysLog> list = jdbcTemplate.query("select * from sys_log", new Object[]{}, new BeanPropertyRowMapper<SysLog>(SysLog.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }

	@Override
	public List<SysLog> find(SysLog sysLog, PageBean pageBean) {
		StringBuffer sb = new StringBuffer("select * from sys_log where 1=1");
		if (sysLog != null) {
			if(StringUtil.isNotEmpty(sysLog.getUserId())){
				sb.append(" and user_id like '%"+sysLog.getUserId()+"%'");
			}
			if(StringUtil.isNotEmpty(sysLog.getUserName())){
				sb.append(" and user_name like '%"+sysLog.getUserName()+"%'");
			}
			if(StringUtil.isNotEmpty(sysLog.getSoftId())){
				sb.append(" and soft_id='"+sysLog.getSoftId()+"'");
			}
			if(StringUtil.isNotEmpty(sysLog.getIpAddress())){
				sb.append(" and ip_address like '%"+sysLog.getIpAddress()+"%'");
			}
			if(StringUtil.isNotEmpty(sysLog.getRequestAddress())){
				sb.append(" and request_address like '%"+sysLog.getRequestAddress()+"%'");
			}
			sb.append(" order by create_time desc");
			sb.append(" limit " + pageBean.getPage() + ","
					+ pageBean.getPageSize());
		}
		List<SysLog> list = jdbcTemplate.query(sb.toString(), new Object[]{}, new BeanPropertyRowMapper<SysLog>(SysLog.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
	}

	@Override
	public int count(SysLog sysLog, PageBean pageBean) {
		StringBuffer sb = new StringBuffer("select count(1) from sys_log where 1=1");
		if (sysLog != null) {
			if(StringUtil.isNotEmpty(sysLog.getUserId())){
				sb.append(" and user_id like '%"+sysLog.getUserId()+"%'");
			}
			if(StringUtil.isNotEmpty(sysLog.getUserName())){
				sb.append(" and user_name like '%"+sysLog.getUserName()+"%'");
			}
			if(StringUtil.isNotEmpty(sysLog.getSoftId())){
				sb.append(" and soft_id='"+sysLog.getSoftId()+"'");
			}
			if(StringUtil.isNotEmpty(sysLog.getIpAddress())){
				sb.append(" and ip_address like '%"+sysLog.getIpAddress()+"%'");
			}
			if(StringUtil.isNotEmpty(sysLog.getRequestAddress())){
				sb.append(" and request_address like '%"+sysLog.getRequestAddress()+"%'");
			}
			sb.append(" order by create_time desc");
		}
		return jdbcTemplate.queryForObject(sb.toString(), Integer.class);
	}

}

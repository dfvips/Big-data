package com.zdata.dao.impl;

import java.util.List;
import java.util.Map;

import com.zdata.dao.SysRemindDao;
import com.zdata.model.SysRemind;
import com.zdata.util.StringUtil;


@Repository("sysRemindDao")
public class SysRemindDaoImpl implements SysRemindDao {

	@Resource
	private JdbcTemplate jdbcTemplate;

    @Override
    public int add(SysRemind sysRemind) {
        return jdbcTemplate.update("insert into sys_remind  (id,func_id,remind_count,user_id,remind_name,soft_id ) values (?,?,?,?,?,? )",
        sysRemind.getId(),sysRemind.getFuncId(),sysRemind.getRemindCount(),sysRemind.getUserId(),sysRemind.getRemindName(),sysRemind.getSoftId());
    }

    @Override
    public int update(SysRemind sysRemind) {
        return jdbcTemplate.update("UPDATE  sys_remind  SET func_id=?,remind_count=?,user_id=?,remind_name=?,soft_id=?"
        +" where id=?",
            sysRemind.getFuncId(),sysRemind.getRemindCount(),sysRemind.getUserId(),sysRemind.getRemindName(),sysRemind.getSoftId(),
            sysRemind.getId())
        ;
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("DELETE from sys_remind where id=?",id);
    }

    @Override
    public SysRemind findById(int id) {
        List<SysRemind> list = jdbcTemplate.query("select * from sys_remind where id=?", new Object[]{id}, new BeanPropertyRowMapper<SysRemind>(SysRemind.class));
        if(list!=null && list.size()>0){
            SysRemind sysRemind = list.get(0);
            return sysRemind;
        }else{
             return null;
        }
    }

    @Override
    public List<SysRemind> findAllList(Map<String,Object> params) {
        List<SysRemind> list = jdbcTemplate.query("select * from sys_remind", new Object[]{}, new BeanPropertyRowMapper<SysRemind>(SysRemind.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }
	
	@Override
	public List<SysRemind> findByAll(SysRemind record) {
		StringBuffer sb = new StringBuffer("select * from sys_remind where 1=1");
		if(record!=null){
			if (record.getSoftId()!=null) {
				sb.append(" and soft_id="+record.getSoftId());
			}
			if (StringUtil.isNotEmpty(record.getUserId())){
				sb.append(" and user_id='"+record.getUserId()+"'");
			}
		}
		List<SysRemind> list = jdbcTemplate.query(sb.toString(), new Object[]{}, new BeanPropertyRowMapper<SysRemind>(SysRemind.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
	}

	@Override
	public int deleteByFuncId(String funcId) {
		return jdbcTemplate.update("DELETE from sys_remind where func_id=?",funcId);
	}

}

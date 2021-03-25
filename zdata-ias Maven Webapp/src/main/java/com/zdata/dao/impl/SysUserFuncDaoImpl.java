package com.zdata.dao.impl;

import java.util.List;
import java.util.Map;

import com.zdata.dao.SysUserFuncDao;
import com.zdata.model.SysUserFunc;

@Repository("sysUserFuncDao")
public class SysUserFuncDaoImpl implements SysUserFuncDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(SysUserFunc sysUserFunc) {
        return jdbcTemplate.update("insert into sys_user_func  (id,user_id,func_id ) values (?,?,? )",
        sysUserFunc.getId(),sysUserFunc.getUserId(),sysUserFunc.getFuncId());
    }

    @Override
    public int update(SysUserFunc sysUserFunc) {
        return jdbcTemplate.update("UPDATE  sys_user_func  SET user_id=?,func_id=?"
        +" where id=?",
            sysUserFunc.getUserId(),sysUserFunc.getFuncId(),
            sysUserFunc.getId())
        ;
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("DELETE from sys_user_func where id=?",id);
    }

    @Override
    public SysUserFunc findById(int id) {
        List<SysUserFunc> list = jdbcTemplate.query("select * from sys_user_func where id=?", new Object[]{id}, new BeanPropertyRowMapper<SysUserFunc>(SysUserFunc.class));
        if(list!=null && list.size()>0){
            SysUserFunc sysUserFunc = list.get(0);
            return sysUserFunc;
        }else{
             return null;
        }
    }

    @Override
    public List<SysUserFunc> findAllList(Map<String,Object> params) {
        List<SysUserFunc> list = jdbcTemplate.query("select * from sys_user_func", new Object[]{}, new BeanPropertyRowMapper<SysUserFunc>(SysUserFunc.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }

	@Override
	public List<SysUserFunc> findByUserId(String userId) {
		List<SysUserFunc> list = jdbcTemplate.query("select * from sys_user_func where user_id='"+userId+"'", new Object[]{}, new BeanPropertyRowMapper<SysUserFunc>(SysUserFunc.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
	}

	@Override
	public int deleteByUserId(String userId) {
		return jdbcTemplate.update("DELETE from sys_user_func where user_id=?",userId);
	}

	@Override
	public int deleteByFuncId(String funcId) {
		return jdbcTemplate.update("DELETE from sys_user_func where func_id=?",funcId);
	}

}

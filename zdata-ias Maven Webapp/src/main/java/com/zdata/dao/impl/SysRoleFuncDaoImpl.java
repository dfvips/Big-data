package com.zdata.dao.impl;

import java.util.List;
import java.util.Map;

import com.zdata.dao.SysRoleFuncDao;
import com.zdata.model.SysRoleFunc;

@Repository("sysRoleFuncDao")
public class SysRoleFuncDaoImpl implements SysRoleFuncDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(SysRoleFunc sysRoleFunc) {
        return jdbcTemplate.update("insert into sys_role_func  (id,role_id,func_id ) values (?,?,? )",
        sysRoleFunc.getId(),sysRoleFunc.getRoleId(),sysRoleFunc.getFuncId());
    }

    @Override
    public int update(SysRoleFunc sysRoleFunc) {
        return jdbcTemplate.update("UPDATE  sys_role_func  SET role_id=?,func_id=?"+" where id=?",
            sysRoleFunc.getRoleId(),sysRoleFunc.getFuncId(),
            sysRoleFunc.getId());
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("DELETE from sys_role_func where id=?",id);
    }

    @Override
    public SysRoleFunc findById(int id) {
        List<SysRoleFunc> list = jdbcTemplate.query("select * from sys_role_func where id=?", new Object[]{id}, new BeanPropertyRowMapper<SysRoleFunc>(SysRoleFunc.class));
        if(list!=null && list.size()>0){
            SysRoleFunc sysRoleFunc = list.get(0);
            return sysRoleFunc;
        }else{
             return null;
        }
    }

    @Override
    public List<SysRoleFunc> findAllList(Map<String,Object> params) {
        List<SysRoleFunc> list = jdbcTemplate.query("select * from sys_role_func", new Object[]{}, new BeanPropertyRowMapper<SysRoleFunc>(SysRoleFunc.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }
    
    @Override
    public int deleteByRoleId(int roleId){
    	return jdbcTemplate.update("DELETE from sys_role_func where role_id=?",roleId);
    }

	@Override
	public List<SysRoleFunc> findAllByRoleIdList(int roleId) {
		List<SysRoleFunc> list = jdbcTemplate.query("select * from sys_role_func where role_id='"+roleId+"'", new Object[]{}, new BeanPropertyRowMapper<SysRoleFunc>(SysRoleFunc.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
	}

	@Override
	public int deleteByFuncId(String funcId) {
		return jdbcTemplate.update("DELETE from sys_role_func where func_id=?",funcId);
	}
}

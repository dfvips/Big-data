package com.zdata.dao.impl;

import java.util.List;
import java.util.Map;

import com.zdata.dao.SysSoftFuncDao;
import com.zdata.model.SysSoftFunc;

@Repository
public class SysSoftFuncDaoImpl implements SysSoftFuncDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(SysSoftFunc sysSoftFunc) {
        return jdbcTemplate.update("insert into sys_soft_func  (id,func_id,soft_id ) values (?,?,? )",
        sysSoftFunc.getId(),sysSoftFunc.getFuncId(),sysSoftFunc.getSoftId());
    }

    @Override
    public int update(SysSoftFunc sysSoftFunc) {
        return jdbcTemplate.update("UPDATE  sys_soft_func  SET func_id=?,soft_id=?"
        +" where id=?",
            sysSoftFunc.getFuncId(),sysSoftFunc.getSoftId(),
            sysSoftFunc.getId())
        ;
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("DELETE from sys_soft_func where id=?",id);
    }

    @Override
    public SysSoftFunc findById(int id) {
        List<SysSoftFunc> list = jdbcTemplate.query("select * from sys_soft_func where id=?", new Object[]{id}, new BeanPropertyRowMapper<SysSoftFunc>(SysSoftFunc.class));
        if(list!=null && list.size()>0){
            SysSoftFunc sysSoftFunc = list.get(0);
            return sysSoftFunc;
        }else{
             return null;
        }
    }

    @Override
    public List<SysSoftFunc> findAllList(Map<String,Object> params) {
        List<SysSoftFunc> list = jdbcTemplate.query("select * from sys_soft_func", new Object[]{}, new BeanPropertyRowMapper<SysSoftFunc>(SysSoftFunc.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }

	@Override
	public int deleteBySoftId(Integer softId) {
		return jdbcTemplate.update("DELETE from sys_soft_func where soft_id=?",softId);
	}

	@Override
	public List<SysSoftFunc> findBySoftId(Integer softId) {
		List<SysSoftFunc> list = jdbcTemplate.query("select * from sys_soft_func where soft_id=?", new Object[]{softId}, new BeanPropertyRowMapper<SysSoftFunc>(SysSoftFunc.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
	}

	@Override
	public int deleteByFuncId(String funcId) {
		return jdbcTemplate.update("DELETE from sys_soft_func where func_id=?",funcId);
	}

}

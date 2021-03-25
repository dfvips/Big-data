package com.zdata.dao.impl;

import java.util.List;
import java.util.Map;

import com.zdata.dao.SysUserDyDao;
import com.zdata.model.SysUserDy;

@Repository("sysUserDyDao")
public class SysUserDyDaoImpl implements SysUserDyDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
    public int add(SysUserDy sysUserDy) {
        return jdbcTemplate.update("insert into sys_user_dy  (id,open_id,user_id,access_token,update_time,client_token ) values (?,?,?,?,?,? )",
        sysUserDy.getId(),sysUserDy.getOpenId(),sysUserDy.getUserId(),sysUserDy.getAccessToken(),sysUserDy.getUpdateTime(),sysUserDy.getClientToken());
    }

    @Override
    public int update(SysUserDy sysUserDy) {
        return jdbcTemplate.update("UPDATE  sys_user_dy  SET open_id=?,user_id=?,access_token=?,update_time=?,client_token=?"
        +" where id=?",
            sysUserDy.getOpenId(),sysUserDy.getUserId(),sysUserDy.getAccessToken(),sysUserDy.getUpdateTime(),sysUserDy.getClientToken(),
            sysUserDy.getId())
        ;
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("DELETE from sys_user_dy where id=?",id);
    }

    @Override
    public SysUserDy findById(int id) {
        List<SysUserDy> list = jdbcTemplate.query("select * from sys_user_dy where id=?", new Object[]{id}, new BeanPropertyRowMapper<SysUserDy>(SysUserDy.class));
        if(list!=null && list.size()>0){
            SysUserDy sysUserDy = list.get(0);
            return sysUserDy;
        }else{
             return null;
        }
    }

    @Override
    public List<SysUserDy> findAllList(Map<String,Object> params) {
        List<SysUserDy> list = jdbcTemplate.query("select * from sys_user_dy", new Object[]{}, new BeanPropertyRowMapper<SysUserDy>(SysUserDy.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }

	@Override
	public SysUserDy findByOpenId(String openId) {
		List<SysUserDy> list = jdbcTemplate.query("select * from sys_user_dy where open_id=?", new Object[]{openId}, new BeanPropertyRowMapper<SysUserDy>(SysUserDy.class));
        if(list!=null && list.size()>0){
            SysUserDy sysUserDy = list.get(0);
            return sysUserDy;
        }else{
             return null;
        }
	}

	@Override
	public SysUserDy findByUserId(String userId) {
		List<SysUserDy> list = jdbcTemplate.query("select * from sys_user_dy where user_id=?", new Object[]{userId}, new BeanPropertyRowMapper<SysUserDy>(SysUserDy.class));
        if(list!=null && list.size()>0){
            SysUserDy sysUserDy = list.get(0);
            return sysUserDy;
        }else{
             return null;
        }
	}

}

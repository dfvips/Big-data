package com.zdata.dao.impl;

import java.util.List;
import java.util.Map;

import com.zdata.dao.SysSoftDao;
import com.zdata.model.SysSoft;
import com.zdata.util.StringUtil;

/**
* sys_soft
* @author 梦丶随心飞 2020-07-18
*/
@Repository("sysSoftDao")
public class SysSoftDaoImpl implements SysSoftDao {

	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(SysSoft sysSoft) {
        return jdbcTemplate.update("insert into sys_soft  (id,soft_id,soft_name,soft_intro,soft_version,soft_create,create_time,web_url,main_url ) values (?,?,?,?,?,?,?,?,?)",
        sysSoft.getId(),sysSoft.getSoftId(),sysSoft.getSoftName(),sysSoft.getSoftIntro(),sysSoft.getSoftVersion(),sysSoft.getSoftCreate(),sysSoft.getCreateTime(),sysSoft.getWebUrl(),sysSoft.getMainUrl());
    }

    @Override
    public int update(SysSoft sysSoft) {
        return jdbcTemplate.update("UPDATE  sys_soft  SET soft_id=?,soft_name=?,soft_intro=?,soft_version=?,soft_create=?,create_time=?,web_url=?,main_url"
        +" where id=?",
            sysSoft.getSoftId(),sysSoft.getSoftName(),sysSoft.getSoftIntro(),sysSoft.getSoftVersion(),sysSoft.getSoftCreate(),sysSoft.getCreateTime(),sysSoft.getWebUrl(),sysSoft.getMainUrl(),
            sysSoft.getId())
        ;
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("DELETE from sys_soft where id=?",id);
    }

    @Override
    public SysSoft findById(int id) {
        List<SysSoft> list = jdbcTemplate.query("select * from sys_soft where id=?", new Object[]{id}, new BeanPropertyRowMapper<SysSoft>(SysSoft.class));
        if(list!=null && list.size()>0){
            SysSoft sysSoft = list.get(0);
            return sysSoft;
        }else{
             return null;
        }
    }

    @Override
    public List<SysSoft> findAllList(Map<String,Object> params) {
        List<SysSoft> list = jdbcTemplate.query("select * from sys_soft", new Object[]{}, new BeanPropertyRowMapper<SysSoft>(SysSoft.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }

	@Override
	public SysSoft findBySoftId(String softId) {
		List<SysSoft> list = jdbcTemplate.query("select * from sys_soft where soft_id=?", new Object[]{softId}, new BeanPropertyRowMapper<SysSoft>(SysSoft.class));
        if(list!=null && list.size()>0){
            SysSoft sysSoft = list.get(0);
            return sysSoft;
        }else{
             return null;
        }
	}

	@Override
	public List<SysSoft> findByAll(SysSoft record) {
		StringBuffer sb = new StringBuffer("select * from sys_soft where 1=1");
		if (record!=null) {
			if (StringUtil.isNotEmpty(record.getSoftId())) {
				sb.append(" and soft_id='"+record.getSoftId()+"'");
			}
			sb.append(" limit "+record.getPage()+","+record.getRows());
		}
		List<SysSoft> list = jdbcTemplate.query(sb.toString(), new Object[]{}, new BeanPropertyRowMapper<SysSoft>(SysSoft.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
             return null;
        }
	}

	@Override
	public int findCountByAll(SysSoft record) {
		StringBuffer sb = new StringBuffer("select count(1) from sys_soft where 1=1");
		if (record!=null) {
			if (StringUtil.isNotEmpty(record.getSoftId())) {
				sb.append(" and soft_id='"+record.getSoftId()+"'");
			}
		}
		return jdbcTemplate.queryForObject(sb.toString(), Integer.class);
	}
	

}

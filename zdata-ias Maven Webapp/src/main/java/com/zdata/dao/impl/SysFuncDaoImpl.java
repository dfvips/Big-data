package com.zdata.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zdata.dao.SysFuncDao;
import com.zdata.model.SysFunc;
import com.zdata.model.SysUser;
import com.zdata.util.DateUtil;

@Repository("sysFuncDao")
public class SysFuncDaoImpl implements SysFuncDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(SysFunc sysFunc) {
        return jdbcTemplate.update("insert into sys_func  (id,func_id,func_name,func_parent_id,func_sort,url,url_basic,url_param,func_img,func_type,is_view,func_des,exchang_time,soft_param,user_param,func_param,is_encrypt ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )",
        sysFunc.getId(),sysFunc.getFuncId(),sysFunc.getFuncName(),sysFunc.getFuncParentId(),sysFunc.getFuncSort(),sysFunc.getUrl(),sysFunc.getUrlBasic(),sysFunc.getUrlParam(),sysFunc.getFuncImg(),sysFunc.getFuncType(),sysFunc.getIsView(),sysFunc.getFuncDes(),sysFunc.getExchangTime(),sysFunc.getSoftParam(),sysFunc.getUserParam(),sysFunc.getFuncParam(),sysFunc.getIsEncrypt());
    }

    @Override
    public int update(SysFunc sysFunc) {
        return jdbcTemplate.update("UPDATE  sys_func  SET func_id=?,func_name=?,func_parent_id=?,func_sort=?,url=?,url_basic=?,url_param=?,func_img=?,func_type=?,is_view=?,func_des=?,exchang_time=?,soft_param=?,user_param=?,func_param=?,is_encrypt=?"
        +" where id=?",
            sysFunc.getFuncId(),sysFunc.getFuncName(),sysFunc.getFuncParentId(),sysFunc.getFuncSort(),sysFunc.getUrl(),sysFunc.getUrlBasic(),sysFunc.getUrlParam(),sysFunc.getFuncImg(),sysFunc.getFuncType(),sysFunc.getIsView(),sysFunc.getFuncDes(),sysFunc.getExchangTime(),sysFunc.getSoftParam(),sysFunc.getUserParam(),sysFunc.getFuncParam(),sysFunc.getIsEncrypt(),
            sysFunc.getId())
        ;
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("DELETE from sys_func where id=?",id);
    }

    @Override
    public SysFunc findById(int id) {
        List<SysFunc> list = jdbcTemplate.query("select * from sys_func where id=?", new Object[]{id}, new BeanPropertyRowMapper<SysFunc>(SysFunc.class));
        if(list!=null && list.size()>0){
            SysFunc sysFunc = list.get(0);
            return sysFunc;
        }else{
             return null;
        }
    }

    @Override
    public List<SysFunc> findAllList(Map<String,Object> params) {
        List<SysFunc> list = jdbcTemplate.query("select * from sys_func", new Object[]{}, new BeanPropertyRowMapper<SysFunc>(SysFunc.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }

	@Override
	public SysFunc findByFuncId(String funcId) {
		List<SysFunc> list = jdbcTemplate.query("select * from sys_func where func_id=?", new Object[]{funcId}, new BeanPropertyRowMapper<SysFunc>(SysFunc.class));
        if(list!=null && list.size()>0){
            SysFunc sysFunc = list.get(0);
            return sysFunc;
        }else{
             return null;
        }
	}

	@Override
	public boolean isParent(String funcId, String userId) {
		String sql = "select count(1) from sys_func t1,sys_user_func t2 where t2.func_id=t1.func_id and t1.func_parent_id=? and t2.user_id=? ";
		int result = jdbcTemplate.queryForObject(sql, new Object[] { funcId,userId },Integer.class);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<SysFunc> findByPidAndUserId(String funcId, String userId, HttpServletRequest request) {
		String sql = "select t2.* from sys_user_func t1,sys_func t2 where t1.func_id=t2.func_id and t1.user_id=? and t2.func_parent_id = ? "
				+ "order by t2.func_sort";
		final List<SysFunc> funcList = new ArrayList<SysFunc>();
		jdbcTemplate.query(sql,
				new Object[] { userId,funcId }, new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						SysFunc func = new SysFunc();
						func.setId(rs.getInt("t2.id"));
						func.setFuncId(rs.getString("t2.func_id"));
						func.setFuncName(rs.getString("t2.func_name"));
						func.setFuncParentId(rs.getString("t2.func_parent_id"));
						func.setFuncSort(rs.getString("t2.func_sort"));
						func.setUrl(rs.getString("t2.url"));
						func.setUrlBasic(rs.getString("t2.url_basic"));
						HttpSession session = request.getSession(true);
						SysUser user = (SysUser) session.getAttribute("curUser");
						String urlParam = rs.getString("t2.url_param")==null?"":rs.getString("t2.url_param");
						//菜单不加密
						if(rs.getInt("t2.is_encrypt")==0){
							func.setFuncParam(rs.getString("t2.soft_param")+"="+user.getSoftId()
									+"&"+rs.getString("t2.user_param")+"="+user.getUserId()
									+"&"+rs.getString("t2.func_param")+"="+rs.getString("t2.func_id")
									+ urlParam
									);
						//菜单用户加密
						}else if(rs.getInt("t2.is_encrypt")==1){
							
							func.setFuncParam(rs.getString("t2.soft_param")+"="+user.getSoftId()
									+"&"+rs.getString("t2.func_param")+"="+rs.getString("t2.func_id")
									+ urlParam
									+"&publicKey="+user.getPublicKey()
									+"&"+rs.getString("t2.user_param")+"="+user.getEncoded()
									);
						}
						func.setIsEncrypt(rs.getInt("t2.is_encrypt"));
						func.setFuncImg(rs.getString("t2.func_img"));
						func.setFuncType(rs.getString("t2.func_type"));
						func.setIsView(rs.getInt("t2.is_view"));
						func.setFuncDes(rs.getString("t2.func_des"));
						func.setExchangTime(DateUtil.formatString(rs.getString("t2.exchang_time"), "yyyy-MM-dd HH:mm:ss"));
						funcList.add(func);
					}
				});
		return pageTree(funcList,userId);
	}

	private List<SysFunc> pageTree(List<SysFunc> funcList, String userId) {
		List<SysFunc> rtn = new ArrayList<SysFunc>();
		for(SysFunc item : funcList){
			if (isParent(item.getFuncId(),userId)) {//判断是否是父节点
				List<SysFunc> children = new ArrayList<SysFunc>();
				item.setIsParent(1);
				item.setChildren(children);
			} else {
				List<SysFunc> children = new ArrayList<SysFunc>();
				item.setIsParent(0);
				item.setChildren(children);
			}
			rtn.add(item);
		}
		return rtn;
	}

	@Override
	public List<SysFunc> findLikeByPid(String pId) {
		List<SysFunc> list = jdbcTemplate.query("select * from sys_func where func_parent_id like '"+pId+"%'", new Object[]{}, new BeanPropertyRowMapper<SysFunc>(SysFunc.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
	}

	@Override
	public int deleteByFuncId(String funcId) {
		return jdbcTemplate.update("DELETE from sys_func where func_id=?",funcId);
	}

	@Override
	public List<SysFunc> findLikeByFuncId(String funcId) {
		List<SysFunc> list = jdbcTemplate.query("select * from sys_func where func_id like '"+funcId+"%'", new Object[]{}, new BeanPropertyRowMapper<SysFunc>(SysFunc.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
             return null;
        }
	}

	@Override
	public boolean isParent(String funcId) {
		String sql = "select count(1) from sys_func where func_parent_id=?";
		int result = jdbcTemplate.queryForObject(sql, new Object[] { funcId },Integer.class);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<SysFunc> findByPid(String pId) {
		List<SysFunc> list = jdbcTemplate.query("select * from sys_func where func_parent_id='"+pId+"' order by func_id desc", new Object[]{}, new BeanPropertyRowMapper<SysFunc>(SysFunc.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
	}

}

package com.zdata.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zdata.dao.SysFileDao;
import com.zdata.model.SysFile;
import com.zdata.util.DateUtil;
import com.zdata.util.StringUtil;

@Repository("sysFileDao")
public class SysFileDaoImpl implements SysFileDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void add(SysFile record) {
		String sql ="insert into sys_file (file_name,file_desc,file_size,bus_id,bus_type,file_time,file_live,user_id,file_sub,file_url) values (?,?,?,?,?,now(),?,?,?,?)";
		jdbcTemplate.update(sql,new Object[] {record.getFileName(),record.getFileDesc(),record.getFileSize(),record.getBusId(),record.getBusType(),record.getFileLive(),record.getUserId(),record.getFileSub(),record.getFileUrl()});
	}

	@Override
	public boolean update(SysFile record) {
		return false;
	}

	@Override
	public void delete(int id) {
		String sql = "delete from sys_file where id=?";
		jdbcTemplate.update(sql, new Object[] { id });
	}

	@Override
	public List<SysFile> find(SysFile record) {
		StringBuffer sb = new StringBuffer("select * from sys_file where 1=1");
		if (record != null) {
			if(StringUtil.isNotEmpty(record.getBusType())){
				sb.append(" and bus_type='"+record.getBusType()+"'");
			}
			if(record.getBusId()!=null){
				sb.append(" and bus_id="+record.getBusId());
			}
			if(StringUtil.isNotEmpty(record.getUserId())){
				sb.append(" and user_id='"+record.getUserId()+"'");
			}
			sb.append(" order by file_time desc");
			sb.append(" limit " + record.getPage() + ","
					+ record.getRows());
		}
		final List<SysFile> sysFileList = new ArrayList<SysFile>();
		jdbcTemplate.query(sb.toString(),
				new Object[] { }, new RowCallbackHandler() {
					@Override
					public void processRow(ResultSet rs) throws SQLException {
						SysFile sysFile = new SysFile();
						sysFile.setId(rs.getInt("id"));
						sysFile.setFileName(rs.getString("file_name"));
						sysFile.setFileDesc(rs.getString("file_desc"));
						sysFile.setFileSize(rs.getString("file_size"));
						sysFile.setBusId(rs.getInt("bus_id"));
						sysFile.setBusType(rs.getString("bus_type"));
						sysFile.setFileTime(DateUtil.formatString(rs.getString("file_time"), "yyyy-mm-dd HH:mm:ss"));
						sysFile.setFileLive(rs.getInt("file_live"));
						sysFile.setUserId(rs.getString("user_id"));
						sysFile.setFileSub(rs.getString("file_sub"));
						sysFile.setFileUrl(rs.getString("file_url"));
						sysFileList.add(sysFile);
					}
		});
		return sysFileList;
	}

	@Override
	public int count(SysFile record) {
		StringBuffer sb = new StringBuffer("select count(1) from sys_file where 1=1");
		if (record != null) {
			if(StringUtil.isNotEmpty(record.getBusType())){
				sb.append(" and bus_type='"+record.getBusType()+"'");
			}
			if(record.getBusId()!=null){
				sb.append(" and bus_id="+record.getBusId());
			}
			if(StringUtil.isNotEmpty(record.getUserId())){
				sb.append(" and user_id='"+record.getUserId()+"'");
			}
			sb.append(" order by file_time desc");
		}
		return jdbcTemplate.queryForObject(sb.toString(), Integer.class);
	}

	@Override
	public void deleteByBus(String busType, int busId) {
		String sql = "delete from sys_file where bus_id=? and bus_type=?";
		jdbcTemplate.update(sql, new Object[] { busId,busType });
	}

}

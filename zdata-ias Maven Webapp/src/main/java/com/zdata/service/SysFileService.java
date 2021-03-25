package com.zdata.service;

import java.util.Map;

import com.zdata.model.SysFile;


public interface SysFileService {

	public Map<String,Object> find(SysFile record,HttpServletRequest request);
	
	public SysFile findOne(Long id);
	
	public Map<String, Object> save(String params,HttpServletRequest request);
	
	public boolean update(SysFile sysFile);
	
	public Map<String, Object> delete(int id);
	
	public Map<String,Object> saveFile(MultipartFile[] file,String num,HttpServletRequest request);
	
	public Map<String,Object> uploadFile(MultipartFile file,HttpServletRequest request);

	public Map<String,Object> uploadImage(MultipartFile file,HttpServletRequest request);
}

package com.zdata.ccc.service;

import com.zdata.ccc.model.YoungWord;
import com.zdata.ccc.result.RetResult;

public interface YoungWordService {

	public RetResult<?> find(YoungWord record) throws Exception;
	
	public RetResult<?> findDetail(Integer id) throws Exception;
	
	public RetResult<?> deleteById(Integer id) throws Exception;
	
	
}

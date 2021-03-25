package com.zdata.yundong.service;

import com.zdata.yundong.model.YoungWord;
import com.zdata.yundong.result.RetResult;

public interface YoungWordService {

	public RetResult<?> find(YoungWord record) throws Exception;
	
	public RetResult<?> findDetail(Integer id) throws Exception;
	
	public RetResult<?> deleteById(Integer id) throws Exception;
	
	
}

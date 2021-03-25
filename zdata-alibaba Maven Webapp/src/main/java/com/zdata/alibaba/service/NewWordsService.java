package com.zdata.alibaba.service;

import com.zdata.alibaba.result.RetResult;
import com.zdata.alibaba.vo.search.NewWordsSearchVo;

public interface NewWordsService {

	public RetResult<?> find(NewWordsSearchVo searchVo) throws Exception;
}

package com.zdata.alibaba.dao;

import java.util.List;

import com.zdata.alibaba.model.RateWords;
import com.zdata.alibaba.vo.search.RateWordsSearchVo;

public interface RateWordsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RateWords record);

    int insertSelective(RateWords record);

    RateWords selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RateWords record);

    int updateByPrimaryKey(RateWords record);
    
    List<RateWords> find(RateWordsSearchVo searchVo);
}
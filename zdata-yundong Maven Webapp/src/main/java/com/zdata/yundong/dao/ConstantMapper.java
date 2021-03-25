package com.zdata.yundong.dao;

import com.zdata.yundong.model.Constant;

public interface ConstantMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Constant record);

    int insertSelective(Constant record);

    Constant selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Constant record);

    int updateByPrimaryKey(Constant record);
    
    Constant selectByWord(String word);
}
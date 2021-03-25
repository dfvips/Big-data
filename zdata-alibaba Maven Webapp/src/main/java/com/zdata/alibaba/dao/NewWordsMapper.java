package com.zdata.alibaba.dao;

import java.util.List;

import com.zdata.alibaba.model.NewWords;
import com.zdata.alibaba.vo.search.NewWordsSearchVo;

public interface NewWordsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NewWords record);

    int insertSelective(NewWords record);

    NewWords selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NewWords record);

    int updateByPrimaryKey(NewWords record);
    
    List<NewWords> find(NewWordsSearchVo searchVo);
}
package com.zdata.alibaba.dao;

import java.util.List;

import com.zdata.alibaba.model.HotWords;
import com.zdata.alibaba.vo.search.HotWordsSearchVo;

public interface HotWordsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HotWords record);

    int insertSelective(HotWords record);

    HotWords selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HotWords record);

    int updateByPrimaryKey(HotWords record);
    
    List<HotWords> find(HotWordsSearchVo searchVo);
}
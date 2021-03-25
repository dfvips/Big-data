package com.zdata.alibaba.dao;

import java.util.List;

import com.zdata.alibaba.model.HotRise;
import com.zdata.alibaba.vo.search.HotRiseSearchVo;

public interface HotRiseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HotRise record);

    int insertSelective(HotRise record);

    HotRise selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HotRise record);

    int updateByPrimaryKey(HotRise record);
    
    HotRise selectByHotRankId(Integer hotRankId);
    
    List<HotRise> find(HotRiseSearchVo searchVo);
}
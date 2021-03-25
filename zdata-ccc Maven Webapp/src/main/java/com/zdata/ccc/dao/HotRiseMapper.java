package com.zdata.ccc.dao;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.zdata.ccc.model.HotRise;
import com.zdata.ccc.vo.search.HotRiseSearchVo;

public interface HotRiseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HotRise record);

    int insertSelective(HotRise record);

    HotRise selectByPrimaryKey(Integer id);
    
    HotRise selectByGoodsName(@Param("goodsName")String goodsName,@Param("riseDate")String riseDate);

    int updateByPrimaryKeySelective(HotRise record);

    int updateByPrimaryKey(HotRise record);
    
    List<HotRise> find(HotRiseSearchVo searchVo);
}
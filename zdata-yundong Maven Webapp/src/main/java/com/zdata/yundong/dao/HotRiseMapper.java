package com.zdata.yundong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zdata.yundong.model.HotRise;
import com.zdata.yundong.vo.search.HotRiseSearchVo;

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
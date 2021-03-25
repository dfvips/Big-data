package com.zdata.yundong.dao;

import java.util.List;

import com.zdata.yundong.model.HotTrend;

public interface HotTrendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HotTrend record);

    int insertSelective(HotTrend record);

    HotTrend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HotTrend record);

    int updateByPrimaryKey(HotTrend record);
    
    /**
     * 
     * @param:        @param hotRiseId
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月29日 上午12:44:58 
     */
    List<HotTrend> findByHotRiseId(Integer hotRiseId);
}
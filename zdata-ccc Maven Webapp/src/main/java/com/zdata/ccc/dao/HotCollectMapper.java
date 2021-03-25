package com.zdata.ccc.dao;

import java.util.List;

import com.zdata.ccc.model.HotCollect;

public interface HotCollectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HotCollect record);

    int insertSelective(HotCollect record);

    HotCollect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HotCollect record);

    int updateByPrimaryKey(HotCollect record);
    
    HotCollect selectByHotRiseId(Integer hotRiseId);
    
    /**
     * 查询列表
     * @param:        @param record
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月23日 下午3:33:31 
     */
    List<HotCollect> findPage(HotCollect record);
}
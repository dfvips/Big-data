package com.zdata.yundong.dao;

import java.util.List;

import com.zdata.yundong.model.YoungTrend;

public interface YoungTrendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YoungTrend record);

    int insertSelective(YoungTrend record);

    YoungTrend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YoungTrend record);

    int updateByPrimaryKey(YoungTrend record);
    
    int deleteByWordId(Integer wordId);
    
    /**
     * 词Id查询
     * @param:        @param wordId
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月27日 下午10:37:51 
     */
    List<YoungTrend> findByWordId(Integer wordId);
    
    /**
     * 
     * @param:        @param catId
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月28日 下午9:51:45 
     */
    int deleteByCatId(Integer catId);
}
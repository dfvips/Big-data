package com.zdata.yundong.dao;

import java.util.List;

import com.zdata.yundong.model.HotWordsCollect;

public interface HotWordsCollectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HotWordsCollect record);

    int insertSelective(HotWordsCollect record);

    HotWordsCollect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HotWordsCollect record);

    int updateByPrimaryKey(HotWordsCollect record);
    
    /**
     * 
     * @param:        @param wordRiseId
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月23日 下午11:48:20 
     */
    HotWordsCollect selectByWordRiseId(Integer wordRiseId);
    
    /**
     * 查询列表
     * @param:        @param record
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月23日 下午11:53:29 
     */
    List<HotWordsCollect> findPage(HotWordsCollect record);
}
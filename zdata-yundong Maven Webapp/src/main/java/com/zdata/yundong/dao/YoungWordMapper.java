package com.zdata.yundong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zdata.yundong.model.YoungWord;

public interface YoungWordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YoungWord record);

    int insertSelective(YoungWord record);

    YoungWord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YoungWord record);

    int updateByPrimaryKey(YoungWord record);
    
    /**
     * 根据状态查询列表
     * @param:        @param state
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月26日 下午6:09:11
     */
    List<YoungWord> findByState(Integer state);
    
    /**
     * 查詢列表
     * @param:        @param record
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月26日 下午9:12:33 
     */
    List<YoungWord> find(YoungWord record);
    
    /**
     * 删除状态为0数据
     * @param:        @param catId
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月28日 下午2:17:36 
     
     */
    int deleteByCatId(@Param("catId") Integer catId);
    
}
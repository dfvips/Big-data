package com.zdata.ccc.dao;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.zdata.ccc.model.Trend;

public interface TrendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Trend record);

    int insertSelective(Trend record);

    Trend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Trend record);

    int updateByPrimaryKey(Trend record);
    
    Trend selectByDate(@Param("updateDate")String updateDate,@Param("catId")Integer catId);
    
    /**
     * 查询子类目增长趋势
     * @param:        @param catId
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月23日 下午4:59:42
     */
    List<Trend> findByCatId(@Param("catId")Integer catId);
}
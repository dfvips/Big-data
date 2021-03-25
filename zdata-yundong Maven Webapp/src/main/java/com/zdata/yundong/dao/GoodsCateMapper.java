package com.zdata.yundong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zdata.yundong.model.GoodsCate;

public interface GoodsCateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsCate record);

    int insertSelective(GoodsCate record);

    GoodsCate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsCate record);

    int updateByPrimaryKey(GoodsCate record);
    
    /**
     * 根据父节点查询列表
     * @param:        @param piId
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月17日 上午10:59:11 
     */
    List<GoodsCate> findByPiId(@Param("piId") Integer piId);
    
    /**
     * 查询全部子类目
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月15日 上午1:52:38 
     */
    List<GoodsCate> find();
}
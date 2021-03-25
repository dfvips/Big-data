package com.zdata.yundong.dao;

import java.util.List;

import com.zdata.yundong.model.Cate;

public interface CateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cate record);

    int insertSelective(Cate record);

    Cate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cate record);

    int updateByPrimaryKey(Cate record);
    
    /**
     * 获取二级类目
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月16日 下午11:03:30 
     */
    List<Cate> findRoot();
    
}
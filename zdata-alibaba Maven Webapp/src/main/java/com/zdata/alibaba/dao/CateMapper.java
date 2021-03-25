package com.zdata.alibaba.dao;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.zdata.alibaba.model.Cate;

public interface CateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cate record);

    int insertSelective(Cate record);

    Cate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cate record);

    int updateByPrimaryKey(Cate record);
    
    List<Cate> findByCatParent(@Param("catParent") Integer catParent);
}
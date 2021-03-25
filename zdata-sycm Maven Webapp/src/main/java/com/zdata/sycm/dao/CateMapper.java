package com.zdata.sycm.dao;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.zdata.sycm.model.Cate;

public interface CateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cate record);

    int insertSelective(Cate record);

    Cate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cate record);

    int updateByPrimaryKey(Cate record);
    
    /**
     * 根据父节点查询列表
     * @param:        @param piId
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月17日 上午10:59:11 
     */
    List<Cate> findByPiId(@Param("piId") Integer piId);
    
    /**
     * 查询全部子类目
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月15日 上午1:52:38 
     */
    List<Cate> find();
}
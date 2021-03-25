package com.zdata.sycm.dao;

import java.util.List;

import com.zdata.sycm.model.SearchWordHot;
import com.zdata.sycm.vo.search.SearchWordHotSearchVo;

public interface SearchWordHotMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SearchWordHot record);

    int insertSelective(SearchWordHot record);

    SearchWordHot selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SearchWordHot record);

    int updateByPrimaryKey(SearchWordHot record);
    
    /**
     * 查询新词
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年10月5日 下午7:11:34 
     */
    List<SearchWordHot> findNew(SearchWordHotSearchVo searchVo);
}
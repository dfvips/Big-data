package com.zdata.sycm.dao;

import java.util.List;

import com.zdata.sycm.model.SearchWordSoar;
import com.zdata.sycm.vo.search.SearchWordSoarSearchVo;

public interface SearchWordSoarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SearchWordSoar record);

    int insertSelective(SearchWordSoar record);

    SearchWordSoar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SearchWordSoar record);

    int updateByPrimaryKey(SearchWordSoar record);
    
    /**
     * 查询新词
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年10月5日 下午7:11:34 
     */
    List<SearchWordSoar> findNew(SearchWordSoarSearchVo searchVo);
}
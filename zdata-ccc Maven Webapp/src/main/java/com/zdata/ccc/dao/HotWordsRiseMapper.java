package com.zdata.ccc.dao;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.zdata.ccc.model.HotWordsRise;
import com.zdata.ccc.vo.search.HotWordsRiseSearchVo;

public interface HotWordsRiseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HotWordsRise record);

    int insertSelective(HotWordsRise record);

    HotWordsRise selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HotWordsRise record);

    int updateByPrimaryKey(HotWordsRise record);
    
    HotWordsRise selectByWord(@Param("word")String word);
    
    List<HotWordsRise> find(HotWordsRiseSearchVo searchVo);
}
package com.zdata.yundong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zdata.yundong.model.HotWordsRise;
import com.zdata.yundong.vo.search.HotWordsRiseSearchVo;

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
package com.zdata.ccc.dao;

import java.util.List;

import com.zdata.ccc.model.WordTrend;
import com.zdata.ccc.vo.search.WordTrendSearchVo;

public interface WordTrendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WordTrend record);

    int insertSelective(WordTrend record);

    WordTrend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WordTrend record);

    int updateByPrimaryKey(WordTrend record);
    
    /**
     * 查询数据
     * @param:        @param record
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月25日 下午6:40:00 
     */
    List<WordTrend> find(WordTrendSearchVo searchVo);
}
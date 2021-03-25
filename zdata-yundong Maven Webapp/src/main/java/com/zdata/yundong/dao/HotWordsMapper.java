package com.zdata.yundong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zdata.yundong.model.HotWords;
import com.zdata.yundong.vo.search.HotWordsSearchVo;

public interface HotWordsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HotWords record);

    int insertSelective(HotWords record);

    HotWords selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HotWords record);

    int updateByPrimaryKey(HotWords record);
    
    List<HotWords> findPage(HotWordsSearchVo searchVo);
    
    List<HotWords> findByCatId(@Param("catId") Integer catId);
    
    /**
     * 查询某一天，某个子类目的的总需求值
     * 搜索热度总和；点击热度总和
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月21日 下午3:24:48 
     */
    HotWords selectByDate(HotWordsSearchVo searchVo);
    
    /**
     * 
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月21日 下午4:00:04 
     */
    List<HotWords> findListByCatId(HotWordsSearchVo searchVo);
    
    /**
     * 按照名称和时间数组查询记录数量
     * @param:        @param words
     * @param:        @param updateDate
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月21日 下午6:50:00 
     */
    List<HotWords> findCountByWords(@Param("words")String[] words,@Param("updateDate")String updateDate);

    /**
     * 查询记录
     * @param:        @param word
     * @param:        @param updateDate
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月21日 下午7:07:36 
     */
	HotWords selectByWord(@Param("word")String word, @Param("updateDate")String updateDate);

	/**
	 * 查询updateDate时间之后的信息
	 * @param:        @param searchVo
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月21日 下午11:10:27 
	 */
	List<HotWords> findAfter(HotWordsSearchVo searchVo);
	
	/**
	 * 查询可能出现的蓝海词
	 * 排除掉了存在常量表Constant中的词
	 * @param:        @param catId
	 * @param:        @return    
	 * @author:       梦丶随心飞
	 * @Date:         2020年8月27日 下午9:26:15 
	 */
	List<HotWords> findYoungByCatId(@Param("catId") Integer catId);
}
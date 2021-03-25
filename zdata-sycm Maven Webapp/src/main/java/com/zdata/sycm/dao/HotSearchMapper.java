package com.zdata.sycm.dao;

import java.util.List;


import com.zdata.sycm.model.HotSearch;
import com.zdata.sycm.vo.search.HotSearchSearchVo;

public interface HotSearchMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HotSearch record);

    int insertSelective(HotSearch record);

    HotSearch selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HotSearch record);

    int updateByPrimaryKey(HotSearch record);
    
    /**
     * 查询列表
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月21日 下午4:24:03 
     */
    List<HotSearch> find(HotSearchSearchVo searchVo);
    
    /**
     * 查询爆款
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月21日 下午5:28:31 
     */
    List<HotSearch> findHot(HotSearchSearchVo searchVo);
    
    /**
     * 根据宝贝Id查询宝贝最近三天记录
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月20日 下午9:39:04 
     */
    List<HotSearch> findByItemId(HotSearchSearchVo searchVo);
}
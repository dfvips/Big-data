package com.zdata.sycm.dao;

import java.util.List;

import com.zdata.sycm.model.HotPurpose;
import com.zdata.sycm.vo.search.HotPurposeSearchVo;

public interface HotPurposeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HotPurpose record);

    int insertSelective(HotPurpose record);

    HotPurpose selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HotPurpose record);

    int updateByPrimaryKey(HotPurpose record);
    
    List<HotPurpose> find(HotPurposeSearchVo earchVo);
    
    /**
     * 查询爆款
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月19日 上午3:15:39 
     */
    List<HotPurpose> findHot(HotPurposeSearchVo searchVo);
    
    /**
     * 根据宝贝Id查询宝贝最近三天记录
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月20日 下午9:39:04 
     */
    List<HotPurpose> findByItemId(HotPurposeSearchVo searchVo);
}
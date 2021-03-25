package com.zdata.ccc.dao;

import java.util.List;

import com.zdata.ccc.model.GroupSurge;
import com.zdata.ccc.vo.search.GroupSurgeSearchVo;

public interface GroupSurgeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupSurge record);

    int insertSelective(GroupSurge record);

    GroupSurge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupSurge record);

    int updateByPrimaryKey(GroupSurge record);
    
    List<GroupSurge> findByUpdateDate(String updateDate);

    /**
     * 查询列表
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月10日 下午3:47:50 
     */
    List<GroupSurge> findListByCatId(GroupSurgeSearchVo searchVo);
    
    /**
     * 按名称查询
     * @param:        @param goodsName
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月11日 下午3:00:56 
     */
    List<GroupSurge> findByGoodsName(String goodsName);
}
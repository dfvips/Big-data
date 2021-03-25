package com.zdata.yundong.dao;

import java.util.List;

import com.zdata.yundong.model.GroupRise;
import com.zdata.yundong.vo.search.GroupRiseSearchVo;

public interface GroupRiseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupRise record);

    int insertSelective(GroupRise record);

    GroupRise selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupRise record);

    int updateByPrimaryKey(GroupRise record);
    
    GroupRise selectByGroupId(Integer groupId);
    
    List<GroupRise> find(GroupRiseSearchVo searchVo);
}
package com.zdata.ccc.dao;

import java.util.List;

import com.zdata.ccc.model.GroupRise;
import com.zdata.ccc.vo.search.GroupRiseSearchVo;

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
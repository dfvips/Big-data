package com.zdata.alibaba.dao;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.zdata.alibaba.model.HotRank;
import com.zdata.alibaba.vo.search.HotRankSearchVo;

public interface HotRankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HotRank record);

    int insertSelective(HotRank record);

    HotRank selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HotRank record);

    int updateByPrimaryKey(HotRank record);
    
    List<HotRank> find(HotRankSearchVo searchVo);
    
    /**
     * 查询新数据
     * @param:        @param crawlTime
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月3日 下午2:32:58 
     */
    List<HotRank> findByCrawlTime(String crawlTime);
    
    /**
     * 商品Id查询数据
     * @param:        @param offerId
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月3日 下午8:17:55
     */
    List<HotRank> findByOfferId(@Param("offerId")Long offerId,@Param("catId")Long catId);
}
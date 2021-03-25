package com.zdata.sycm.dao;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.zdata.sycm.model.HotSale;
import com.zdata.sycm.vo.search.HotSaleSearchVo;

public interface HotSaleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HotSale record);

    int insertSelective(HotSale record);

    HotSale selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HotSale record);

    int updateByPrimaryKey(HotSale record);
    
    /**
     * @param:        @param saleSearchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月17日 下午9:27:07 
     */
    List<HotSale> find(HotSaleSearchVo searchVo);
    
    /**
     * 查询爆款
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月19日 上午3:15:39 
     */
    List<HotSale> findHot(HotSaleSearchVo searchVo);
    
    /**
     * 根据宝贝Id查询宝贝最近三天记录
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月20日 下午9:39:04 
     */
    List<HotSale> findByItemId(HotSaleSearchVo searchVo);
    
    /**
     * 数组查询
     * @param:        @param itemIds
     * @param:        @param catId
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年10月4日 上午2:14:44 
     */
    List<HotSale> findByItemIds(@Param("itemIds")String[] itemIds,@Param("catId")Integer catId);
}
package com.zdata.ccc.dao;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.zdata.ccc.model.HotGoods;
import com.zdata.ccc.vo.search.HotGoodsSearchVo;

public interface HotGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HotGoods record);

    int insertSelective(HotGoods record);

    HotGoods selectByPrimaryKey(Integer id);
    
    HotGoods selectByGoodsName(@Param("goodsName")String goodsName,@Param("updateDate")String updateDate);

    int updateByPrimaryKeySelective(HotGoods record);

    int updateByPrimaryKey(HotGoods record);
    
    List<HotGoods> findByCatId(@Param("catId") Integer catId);
    
    /**
     * 通过名称统计随时间增长变化
     * @param:        @param goodsName
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月19日 下午2:40:15 
     */
    List<HotGoods> findInfoByGoodsName(@Param("goodsName") String goodsName);
    
    /**
     * 查询列表
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月19日 下午2:27:24 
     */
    List<HotGoods> findListByCatId(HotGoodsSearchVo searchVo);
    
    /**
     * 类目Id查询当天上榜链接占大盘数值
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月18日 下午11:01:02 
     */
    List<HotGoods> findPieCatId(HotGoodsSearchVo searchVo);
    
    /**
     * 查询大盘总值
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月19日 上午12:41:11 
     */
    List<HotGoods> findByDate(HotGoodsSearchVo searchVo);
    
    /**
     * 按照名称数组查询记录数量
     * @param:        @param goodsNames
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月19日 下午2:51:35 
     */
    List<HotGoods> findCountByGoodsName(@Param("goodsNames")String[] goodsNames,@Param("updateDate")String updateDate);
    
    /**
     * 查询updateDate时间之后的信息
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年8月19日 下午10:22:10 
     */
    List<HotGoods> findAfter(HotGoodsSearchVo searchVo);
    
    /**
     * 
     * @param:        @param goodsName
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月11日 下午6:52:07 
     */
    List<HotGoods> findByGoodsName(String goodsName);
    
    /**
     * 时间查询上榜数据
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月14日 下午10:33:42 
     */
    List<HotGoods> findProgress(HotGoodsSearchVo searchVo);
    
    /**
     * 获取连续上榜三天数据
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月24日 下午4:57:01 
     */
    List<HotGoods> findHot(HotGoodsSearchVo searchVo);
    
    /**
     * 查询三天数据
     * @param:        @param searchVo
     * @param:        @return    
     * @author:       梦丶随心飞
     * @Date:         2020年9月24日 下午8:31:12 
     */
    List<HotGoods> findThree(HotGoodsSearchVo searchVo);
}
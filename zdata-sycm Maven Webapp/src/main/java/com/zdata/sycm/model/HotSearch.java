package com.zdata.sycm.model;

import java.io.Serializable;
import java.util.Date;

public class HotSearch implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer catId;

    private String itemId;

    private String pictUrl;

    private String detailUrl;

    private String itemTitle;

    private String b2cShop;

    private String pictureUrl;

    private String shopTitle;

    private String shopUrl;

    private String userId;

    private String tradeIndex;

    private Integer cateRank;

    private String seIpvUvHits;

    private String uvIndex;

    private Date updateTime;

    private Date crawlTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public String getPictUrl() {
        return pictUrl;
    }

    public void setPictUrl(String pictUrl) {
        this.pictUrl = pictUrl == null ? null : pictUrl.trim();
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl == null ? null : detailUrl.trim();
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle == null ? null : itemTitle.trim();
    }

    public String getB2cShop() {
        return b2cShop;
    }

    public void setB2cShop(String b2cShop) {
        this.b2cShop = b2cShop == null ? null : b2cShop.trim();
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl == null ? null : pictureUrl.trim();
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle == null ? null : shopTitle.trim();
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl == null ? null : shopUrl.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getTradeIndex() {
        return tradeIndex;
    }

    public void setTradeIndex(String tradeIndex) {
        this.tradeIndex = tradeIndex == null ? null : tradeIndex.trim();
    }

    public Integer getCateRank() {
        return cateRank;
    }

    public void setCateRank(Integer cateRank) {
        this.cateRank = cateRank;
    }

    public String getSeIpvUvHits() {
        return seIpvUvHits;
    }

    public void setSeIpvUvHits(String seIpvUvHits) {
        this.seIpvUvHits = seIpvUvHits == null ? null : seIpvUvHits.trim();
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex == null ? null : uvIndex.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCrawlTime() {
        return crawlTime;
    }

    public void setCrawlTime(Date crawlTime) {
        this.crawlTime = crawlTime;
    }
}
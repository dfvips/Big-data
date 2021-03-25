package com.zdata.sycm.model;

import java.io.Serializable;
import java.util.Date;

public class HotPurpose implements Serializable{
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

    private String pictureUrl;

    private String shopTitle;

    private String shopUrl;

    private String userId;

    private String tradeIndex;

    private Integer cateRank;

    private Integer cateRankCqc;

    private String cltHits;

    private String cartHits;

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

    public Integer getCateRankCqc() {
        return cateRankCqc;
    }

    public void setCateRankCqc(Integer cateRankCqc) {
        this.cateRankCqc = cateRankCqc;
    }

    public String getCltHits() {
        return cltHits;
    }

    public void setCltHits(String cltHits) {
        this.cltHits = cltHits == null ? null : cltHits.trim();
    }

    public String getCartHits() {
        return cartHits;
    }

    public void setCartHits(String cartHits) {
        this.cartHits = cartHits == null ? null : cartHits.trim();
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
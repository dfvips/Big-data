package com.zdata.ccc.model;

import java.util.Date;

public class WordTrend {
    private Integer id;

    private Integer wordId;

    private String word;

    private Integer pv;

    private Integer clickNum;

    private String ctr;

    private String cvr;

    private String competeValue;

    private String imprAvgBid;

    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWordId() {
        return wordId;
    }

    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word == null ? null : word.trim();
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public String getCtr() {
        return ctr;
    }

    public void setCtr(String ctr) {
        this.ctr = ctr == null ? null : ctr.trim();
    }

    public String getCvr() {
        return cvr;
    }

    public void setCvr(String cvr) {
        this.cvr = cvr == null ? null : cvr.trim();
    }

    public String getCompeteValue() {
        return competeValue;
    }

    public void setCompeteValue(String competeValue) {
        this.competeValue = competeValue == null ? null : competeValue.trim();
    }

    public String getImprAvgBid() {
        return imprAvgBid;
    }

    public void setImprAvgBid(String imprAvgBid) {
        this.imprAvgBid = imprAvgBid == null ? null : imprAvgBid.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
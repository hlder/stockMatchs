package com.hld.stockserver.bean;

public class StockJrjSellBuyBean {
    private String tradeType;
    private String tradeTime;
    private String tradeNum;
    private String tradePx;
    public StockJrjSellBuyBean(){}
    public StockJrjSellBuyBean(String tradeType, String tradeTime, String tradeNum, String tradePx) {
        this.tradeType = tradeType;
        this.tradeTime = tradeTime;
        this.tradeNum = tradeNum;
        this.tradePx = tradePx;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum;
    }

    public String getTradePx() {
        return tradePx;
    }

    public void setTradePx(String tradePx) {
        this.tradePx = tradePx;
    }
}

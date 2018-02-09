package com.hld.stockserver.bean;

import java.util.List;

public class StockJrjBean {
    private String securityCode;
    private String securityCodeStr;
    private String date;
    private String time;
    private String securityName;
    private String securityType;
    private String marketType;
    private String tradingStatus;
    private String preClosePx;
    private String openPx;
    private String highPx;
    private String lowPx;
    private String lastPx;
    private String pxChg;
    private String pxChgRatio;
    private String marketValue;
    private String circulationMarketValue;
    private String pe;
    private String innerVolume;
    private String outerVolume;
    private String turnOver;
    private String tradeVolume;
    private String tradeValue;
    private String pxAmplitude;
    private String netValue;
    private String hardenPrice;
    private String dropstopPrice;
    private String marketstatus;
    private String llPFShare;
    private String nLiangbi;
    private String nWeibi;
    private String llTotalShare;
    private String nEPS;
    private String accumNet;

    private List<StockJrjSellBuyBean> sell;
    private List<StockJrjSellBuyBean> buy;
    private long updateTime;

    public StockJrjBean(){

    }
    public StockJrjBean(String securityCode, String date, String time, String securityName, String securityType, String marketType, String tradingStatus, String preClosePx, String openPx, String highPx, String lowPx, String lastPx, String pxChg, String pxChgRatio, String marketValue, String circulationMarketValue, String pe, String innerVolume, String outerVolume, String turnOver, String tradeVolume, String tradeValue, String pxAmplitude, String netValue, String hardenPrice, String dropstopPrice, String marketstatus, String llPFShare, String nLiangbi, String nWeibi, String llTotalShare, String nEPS, String accumNet){
        this.securityCode = securityCode;
        this.date = date;
        this.time = time;
        this.securityName = securityName;
        this.securityType = securityType;
        this.marketType = marketType;
        this.tradingStatus = tradingStatus;
        this.preClosePx = preClosePx;
        this.openPx = openPx;
        this.highPx = highPx;
        this.lowPx = lowPx;
        this.lastPx = lastPx;
        this.pxChg = pxChg;
        this.pxChgRatio = pxChgRatio;
        this.marketValue = marketValue;
        this.circulationMarketValue = circulationMarketValue;
        this.pe = pe;
        this.innerVolume = innerVolume;
        this.outerVolume = outerVolume;
        this.turnOver = turnOver;
        this.tradeVolume = tradeVolume;
        this.tradeValue = tradeValue;
        this.pxAmplitude = pxAmplitude;
        this.netValue = netValue;
        this.hardenPrice = hardenPrice;
        this.dropstopPrice = dropstopPrice;
        this.marketstatus = marketstatus;
        this.llPFShare = llPFShare;
        this.nLiangbi = nLiangbi;
        this.nWeibi = nWeibi;
        this.llTotalShare = llTotalShare;
        this.nEPS = nEPS;
        this.accumNet = accumNet;
    }
    public StockJrjBean(String securityCode, String date, String time, String securityName, String securityType, String marketType, String tradingStatus, String preClosePx, String openPx, String highPx, String lowPx, String lastPx, String pxChg, String pxChgRatio, String marketValue, String circulationMarketValue, String pe, String innerVolume, String outerVolume, String turnOver, String tradeVolume, String tradeValue, String pxAmplitude, String netValue, String hardenPrice, String dropstopPrice, String marketstatus, String llPFShare, String nLiangbi, String nWeibi, String llTotalShare, String nEPS, String accumNet, List<StockJrjSellBuyBean> sell, List<StockJrjSellBuyBean> buy) {
        this.securityCode = securityCode;
        this.date = date;
        this.time = time;
        this.securityName = securityName;
        this.securityType = securityType;
        this.marketType = marketType;
        this.tradingStatus = tradingStatus;
        this.preClosePx = preClosePx;
        this.openPx = openPx;
        this.highPx = highPx;
        this.lowPx = lowPx;
        this.lastPx = lastPx;
        this.pxChg = pxChg;
        this.pxChgRatio = pxChgRatio;
        this.marketValue = marketValue;
        this.circulationMarketValue = circulationMarketValue;
        this.pe = pe;
        this.innerVolume = innerVolume;
        this.outerVolume = outerVolume;
        this.turnOver = turnOver;
        this.tradeVolume = tradeVolume;
        this.tradeValue = tradeValue;
        this.pxAmplitude = pxAmplitude;
        this.netValue = netValue;
        this.hardenPrice = hardenPrice;
        this.dropstopPrice = dropstopPrice;
        this.marketstatus = marketstatus;
        this.llPFShare = llPFShare;
        this.nLiangbi = nLiangbi;
        this.nWeibi = nWeibi;
        this.llTotalShare = llTotalShare;
        this.nEPS = nEPS;
        this.accumNet = accumNet;
        this.sell = sell;
        this.buy = buy;
    }

    public String getSecurityCodeStr() {
        return securityCodeStr;
    }

    public void setSecurityCodeStr(String securityCodeStr) {
        this.securityCodeStr = securityCodeStr;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public List<StockJrjSellBuyBean> getSell() {
        return sell;
    }

    public void setSell(List<StockJrjSellBuyBean> sell) {
        this.sell = sell;
    }

    public List<StockJrjSellBuyBean> getBuy() {
        return buy;
    }

    public void setBuy(List<StockJrjSellBuyBean> buy) {
        this.buy = buy;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public String getTradingStatus() {
        return tradingStatus;
    }

    public void setTradingStatus(String tradingStatus) {
        this.tradingStatus = tradingStatus;
    }

    public String getPreClosePx() {
        return preClosePx;
    }

    public void setPreClosePx(String preClosePx) {
        this.preClosePx = preClosePx;
    }

    public String getOpenPx() {
        return openPx;
    }

    public void setOpenPx(String openPx) {
        this.openPx = openPx;
    }

    public String getHighPx() {
        return highPx;
    }

    public void setHighPx(String highPx) {
        this.highPx = highPx;
    }

    public String getLowPx() {
        return lowPx;
    }

    public void setLowPx(String lowPx) {
        this.lowPx = lowPx;
    }

    public String getLastPx() {
        return lastPx;
    }

    public void setLastPx(String lastPx) {
        this.lastPx = lastPx;
    }

    public String getPxChg() {
        return pxChg;
    }

    public void setPxChg(String pxChg) {
        this.pxChg = pxChg;
    }

    public String getPxChgRatio() {
        return pxChgRatio;
    }

    public void setPxChgRatio(String pxChgRatio) {
        this.pxChgRatio = pxChgRatio;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getCirculationMarketValue() {
        return circulationMarketValue;
    }

    public void setCirculationMarketValue(String circulationMarketValue) {
        this.circulationMarketValue = circulationMarketValue;
    }

    public String getPe() {
        return pe;
    }

    public void setPe(String pe) {
        this.pe = pe;
    }

    public String getInnerVolume() {
        return innerVolume;
    }

    public void setInnerVolume(String innerVolume) {
        this.innerVolume = innerVolume;
    }

    public String getOuterVolume() {
        return outerVolume;
    }

    public void setOuterVolume(String outerVolume) {
        this.outerVolume = outerVolume;
    }

    public String getTurnOver() {
        return turnOver;
    }

    public void setTurnOver(String turnOver) {
        this.turnOver = turnOver;
    }

    public String getTradeVolume() {
        return tradeVolume;
    }

    public void setTradeVolume(String tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    public String getTradeValue() {
        return tradeValue;
    }

    public void setTradeValue(String tradeValue) {
        this.tradeValue = tradeValue;
    }

    public String getPxAmplitude() {
        return pxAmplitude;
    }

    public void setPxAmplitude(String pxAmplitude) {
        this.pxAmplitude = pxAmplitude;
    }

    public String getNetValue() {
        return netValue;
    }

    public void setNetValue(String netValue) {
        this.netValue = netValue;
    }

    public String getHardenPrice() {
        return hardenPrice;
    }

    public void setHardenPrice(String hardenPrice) {
        this.hardenPrice = hardenPrice;
    }

    public String getDropstopPrice() {
        return dropstopPrice;
    }

    public void setDropstopPrice(String dropstopPrice) {
        this.dropstopPrice = dropstopPrice;
    }

    public String getMarketstatus() {
        return marketstatus;
    }

    public void setMarketstatus(String marketstatus) {
        this.marketstatus = marketstatus;
    }

    public String getLlPFShare() {
        return llPFShare;
    }

    public void setLlPFShare(String llPFShare) {
        this.llPFShare = llPFShare;
    }

    public String getnLiangbi() {
        return nLiangbi;
    }

    public void setnLiangbi(String nLiangbi) {
        this.nLiangbi = nLiangbi;
    }

    public String getnWeibi() {
        return nWeibi;
    }

    public void setnWeibi(String nWeibi) {
        this.nWeibi = nWeibi;
    }

    public String getLlTotalShare() {
        return llTotalShare;
    }

    public void setLlTotalShare(String llTotalShare) {
        this.llTotalShare = llTotalShare;
    }

    public String getnEPS() {
        return nEPS;
    }

    public void setnEPS(String nEPS) {
        this.nEPS = nEPS;
    }

    public String getAccumNet() {
        return accumNet;
    }

    public void setAccumNet(String accumNet) {
        this.accumNet = accumNet;
    }
}

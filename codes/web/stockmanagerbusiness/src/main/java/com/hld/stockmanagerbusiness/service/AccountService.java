package com.hld.stockmanagerbusiness.service;

import com.hld.stockmanagerbusiness.bean.EntrustStockInfo;
import com.hld.stockmanagerbusiness.bean.EntrustStockInfoHistory;

import java.util.List;
import java.util.Map;

public interface AccountService {
    //查询表里面所有委托列表
    List<EntrustStockInfo> queryAllEntrust();

    //查询此账户的持仓信息
    Map<String,Object> queryHolderInfos(String accountId);

    //查询我的委托列表
    List<EntrustStockInfo> queryMyEntrust(String accountId);

    //根据委托时间,查询我的委托历史表
    List<Object> queryMyEntrustHistory(String accountId, String startDate, String endDate, int page);

    void buyStock(EntrustStockInfo info,String buyPrice);
    void sellStock(EntrustStockInfo info,String sellPrice);
    //撤单
    boolean revokeMyEntrust(String entrustId);


    //购买股票
    int entrustBuyStock(String accountId,String stockCode,String stockCodeStr,String stockName,String entrustPrice,int count);
    //卖出股票
    int entrustSellStock(String accountId,String stockCode,String stockCodeStr,String stockName,String entrustPrice,int count);

    //模糊搜索股票信息
    List<Map<String,Object>> queryStockFuzzy(String searchStr);
}

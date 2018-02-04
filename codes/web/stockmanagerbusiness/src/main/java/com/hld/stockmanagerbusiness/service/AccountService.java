package com.hld.stockmanagerbusiness.service;

import com.hld.stockmanagerbusiness.bean.EntrustStockInfo;
import com.hld.stockmanagerbusiness.bean.EntrustStockInfoHistory;

import java.util.List;
import java.util.Map;

public interface AccountService {
    //查询此账户的持仓信息
    Map<String,Object> queryHolderInfos(String accountId);

    //查询我的委托列表
    List<EntrustStockInfo> queryMyEntrust(String accountId);

    //根据委托时间,查询我的委托历史表
    List<Object> queryMyEntrustHistory(String accountId, String startDate, String endDate, int page);

    //撤单
    boolean revokeMyEntrust(String accountId,String entrustId);
}

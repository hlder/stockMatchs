package com.hld.stockmanagerbusiness.service;

import java.util.Map;

public interface UserStockService {

    //查询我的持仓信息
    Map<String,Object> queryMyHolderStocks(String userId);
}

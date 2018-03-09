package com.hld.stockserver.service;

import com.hld.stockserver.bean.StockBean;
import com.hld.stockserver.bean.StockJrjBean;

import java.util.List;

public interface StockService {
    List<StockBean> searchStock(String queryStr);
    StockJrjBean queryStockInfoByCode(String str);
}

package com.hld.stockmanagerbusiness.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface MarketService {
    String loadMarketIndex(HttpServletRequest request);
    String queryStockNameByCodes(HttpServletRequest request);
    String queryStockRankUp(HttpServletRequest request);
    String queryStockRankDown(HttpServletRequest request);
}

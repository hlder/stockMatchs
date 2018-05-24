package com.hld.stockmanagerbusiness.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface MarketService {
    Map<String,Object> loadMarketIndex(HttpServletRequest request);
}

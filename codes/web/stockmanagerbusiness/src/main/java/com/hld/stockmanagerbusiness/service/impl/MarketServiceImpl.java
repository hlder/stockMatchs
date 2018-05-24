package com.hld.stockmanagerbusiness.service.impl;

import com.hld.stockmanagerbusiness.service.MarketService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class MarketServiceImpl implements MarketService {

    @Override
    public Map<String, Object> loadMarketIndex(HttpServletRequest request) {
        return null;
    }
}

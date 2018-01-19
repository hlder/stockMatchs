package com.hld.stockmanagerbusiness.service.impl;

import com.hld.stockmanagerbusiness.mapper.UserStockMapper;
import com.hld.stockmanagerbusiness.service.RedisService;
import com.hld.stockmanagerbusiness.service.UserStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserStockServiceImpl implements UserStockService {
    @Autowired
    UserStockMapper userStockMapper;

    //查询我的持仓信息
    @Override
    public Map<String, Object> queryMyHolderStocks(String userId) {

        return null;
    }
}

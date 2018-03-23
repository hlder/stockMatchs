package com.hld.stockweb.service.impl;

import com.hld.stockweb.bean.UserInfoBean;
import com.hld.stockweb.bean.UserShopHisBean;
import com.hld.stockweb.mapper.IndexMapper;
import com.hld.stockweb.service.IndexService;
import com.hld.stockweb.util.BaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    IndexMapper indexMapper;

    @Override
    public Map<String, Object> queryIndexInfo(UserInfoBean bean) {
        String matchId=bean.getMatch_id()+"";
        Map<String,Object> dataMap=new HashMap<>();

        Map<String,Object> map = indexMapper.queryMatchIncomeInfo(""+matchId);
        List<UserShopHisBean> list=indexMapper.queryMathcShopList(""+matchId);
        dataMap.put("data",map);
        dataMap.put("list",list);
        return BaseRequest.getSuccessMap(dataMap);
    }
}

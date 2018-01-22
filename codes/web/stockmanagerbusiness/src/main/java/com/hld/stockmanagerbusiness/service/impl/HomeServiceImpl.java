package com.hld.stockmanagerbusiness.service.impl;

import com.hld.stockmanagerbusiness.bean.MatchInfo;
import com.hld.stockmanagerbusiness.mapper.MathMapper;
import com.hld.stockmanagerbusiness.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    MathMapper mathMapper;

    @Override
    public Map<String, Object> queryHomeInfo(String matchId,String userId,String accountId) {
        Map<String,Object> map=new HashMap<String,Object>();
        //查询比赛的信息，banners，buttons
        MatchInfo matchInfo=mathMapper.queryApplyMatchInfo(matchId);
        if(matchInfo==null){
            return null;
        }
        map.put("matchId",matchInfo.getId()+"");
        map.put("banners",matchInfo.getBanners()+"");//banner条
        map.put("buttons",matchInfo.getButtons()+"");//按钮
        //查询我的记录
        map.put("accountId","1");
        map.put("accountName","张三");
        map.put("allAsset","2000");//总资产
        map.put("priceTimesOneMonth","20");//交易次数
        map.put("rightRate","0.1008");//成功率
        map.put("allIncomeRate","0.0102");//总收益率
        map.put("allIncome","20102");//总收入
        map.put("incomeRateMonth","0.0010");//本月收益率
        map.put("incomeMonth","2000");//本月收益
        map.put("incomeRateWeek","0.2001");//本周收益率
        map.put("incomeWeek","8008");//本周收益

        return map;
    }
}

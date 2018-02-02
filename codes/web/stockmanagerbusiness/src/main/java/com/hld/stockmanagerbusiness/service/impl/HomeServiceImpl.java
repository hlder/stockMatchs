package com.hld.stockmanagerbusiness.service.impl;

import com.hld.stockmanagerbusiness.bean.AccountInfo;
import com.hld.stockmanagerbusiness.bean.MatchInfo;
import com.hld.stockmanagerbusiness.mapper.AccountMapper;
import com.hld.stockmanagerbusiness.mapper.MathMapper;
import com.hld.stockmanagerbusiness.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    MathMapper mathMapper;
    @Autowired
    AccountMapper accountMapper;

    @Override
    public Map<String, Object> queryHomeInfo(String matchId,String userId,String accountId) {
        Map<String,Object> map=new HashMap<String,Object>();
        //查询比赛的信息，banners，buttons
        MatchInfo matchInfo=mathMapper.queryApplyMatchInfo(matchId);
        //查询我的账户信息
        AccountInfo accountInfo=accountMapper.queryAccountById(accountId);
        if(matchInfo==null){
            return null;
        }
        if(accountInfo==null){
            return null;
        }

        DecimalFormat df = new DecimalFormat("0.00");

        map.put("matchId",matchInfo.getId()+"");
        map.put("banners",matchInfo.getBanners()+"");//banner条
        map.put("buttons",matchInfo.getButtons()+"");//按钮
        map.put("macthName",""+matchInfo.getMatch_name());//比赛名字
        map.put("matchLogo",""+matchInfo.getLogo());

        //查询我的记录
        map.put("accountId",""+accountInfo.getId());
        map.put("accountName",""+accountInfo.getAccount_name());
        map.put("allAsset",""+accountInfo.getTotal_assets());//总资产
        map.put("priceTimesOneMonth",""+accountInfo.getDeal_count());//交易次数
        map.put("rightRate",""+(accountInfo.getTotal_deal_success_num()/accountInfo.getDeal_count()));//成功率
        map.put("allIncomeRate",""+df.format(accountInfo.getTotal_income_rate()*100)+"%");//总收益率
        map.put("allIncome",""+accountInfo.getTotal_income());//总收入
        map.put("incomeRateMonth",""+df.format(accountInfo.getMonth_income_rate()*100)+"%");//本月收益率
        map.put("incomeMonth",""+accountInfo.getMonth_income());//本月收益
        map.put("incomeRateWeek",""+df.format(accountInfo.getWeek_income_rate()*100)+"%");//本周收益率
        map.put("incomeWeek",""+accountInfo.getWeek_income());//本周收益
        return map;
    }
}




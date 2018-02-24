package com.hld.stockmanagerbusiness.service.impl;

import com.hld.stockmanagerbusiness.bean.AccountInfo;
import com.hld.stockmanagerbusiness.bean.MatchInfo;
import com.hld.stockmanagerbusiness.mapper.AccountMapper;
import com.hld.stockmanagerbusiness.mapper.MathMapper;
import com.hld.stockmanagerbusiness.mapper.StockInfoMapper;
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
    @Autowired
    StockInfoMapper stockInfoMapper;

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

        float allHolderAssets= stockInfoMapper.queryUserAllValue(accountId);//持仓市值
        float canUseAssets = Float.parseFloat(accountInfo.getCan_use_assets());//可用资产
        float initAllAssets=Float.parseFloat(accountInfo.getInit_total_assets());//初始总资产

        DecimalFormat df = new DecimalFormat("0.00");

        map.put("matchId",matchInfo.getId()+"");
        map.put("banners",matchInfo.getBanners()+"");//banner条
        map.put("buttons",matchInfo.getButtons()+"");//按钮
        map.put("macthName",""+matchInfo.getMatch_name());//比赛名字
        map.put("matchLogo",""+matchInfo.getLogo());

        //查询我的记录
        map.put("accountId",""+accountInfo.getId());
        map.put("accountName",""+accountInfo.getAccount_name());
        map.put("allAsset",""+(canUseAssets+allHolderAssets));//总资产
        map.put("priceTimesOneMonth",""+accountInfo.getDeal_count());//交易次数

        if(accountInfo.getDeal_count()==0){
            map.put("rightRate","0");//成功率
        }else{
            map.put("rightRate",""+(accountInfo.getTotal_deal_success_num()/accountInfo.getDeal_count()));//成功率
        }
        float allIncomeRate=(canUseAssets+allHolderAssets-initAllAssets)/initAllAssets;
        float allIncome=(canUseAssets+allHolderAssets-initAllAssets);

        map.put("allIncomeRate",""+df.format((allIncomeRate)*100)+"%");//总收益率
        map.put("allIncome",""+allIncome);//总收入

        if(accountInfo.getMonth_income()<=0){
            map.put("incomeRateMonth",""+df.format((allIncomeRate)*100)+"%");//总收益率
            map.put("incomeMonth",""+allIncome);//总收入
        }else{
            map.put("incomeRateMonth",""+df.format(accountInfo.getMonth_income_rate()*100)+"%");//本月收益率
            map.put("incomeMonth",""+accountInfo.getMonth_income());//本月收益
        }
        if(accountInfo.getWeek_income()<=0){
            map.put("incomeRateWeek",""+df.format((allIncomeRate)*100)+"%");//总收益率
            map.put("incomeWeek",""+allIncome);//总收入
        }else{
            map.put("incomeRateWeek",""+df.format(accountInfo.getWeek_income_rate()*100)+"%");//本周收益率
            map.put("incomeWeek",""+accountInfo.getWeek_income());//本周收益
        }
        return map;
    }
}




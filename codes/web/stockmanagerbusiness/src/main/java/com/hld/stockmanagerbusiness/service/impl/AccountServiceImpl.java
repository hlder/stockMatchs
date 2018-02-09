package com.hld.stockmanagerbusiness.service.impl;

import com.hld.stockmanagerbusiness.bean.AccountInfo;
import com.hld.stockmanagerbusiness.bean.EntrustStockInfo;
import com.hld.stockmanagerbusiness.bean.EntrustStockInfoHistory;
import com.hld.stockmanagerbusiness.bean.HolderInfo;
import com.hld.stockmanagerbusiness.controller.BaseController;
import com.hld.stockmanagerbusiness.mapper.AccountMapper;
import com.hld.stockmanagerbusiness.mapper.EntrustMapper;
import com.hld.stockmanagerbusiness.mapper.StockInfoMapper;
import com.hld.stockmanagerbusiness.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    StockInfoMapper stockInfoMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    EntrustMapper entrustMapper;

    DecimalFormat df = new DecimalFormat("0.00");
    @Override
    public Map<String,Object> queryHolderInfos(String accountId){
        Map<String,Object> map=new HashMap<>();
        List<HolderInfo> listData = stockInfoMapper.queryMyHolderByAccountId(accountId);//拿到账户的所有持仓信息

        List<Map<String,Object>> listDataMap=new ArrayList<>();
        float holderFund=0;
        for(HolderInfo holderInfo:listData){
            float price=0;
            try{price=Float.parseFloat(""+holderInfo.getNow_price());}catch (NumberFormatException e){}
            holderFund+=price;

            Map<String,Object> temItemMap=new HashMap<>();
            temItemMap.put("stockCode",""+holderInfo.getStock_code());
            temItemMap.put("stockCodeStr",""+holderInfo.getStock_code_str());
            temItemMap.put("stockName",""+holderInfo.getStock_name());//股票名称
            temItemMap.put("nowPrice",""+holderInfo.getNow_price());//现价
            temItemMap.put("costPrice",""+holderInfo.getCost_price());//成本
            temItemMap.put("canUseNum",""+holderInfo.getUsable_num());//可用
            temItemMap.put("holderNum",""+holderInfo.getHolder_num());//持有

            float shizhi=0;
            float chengben=0;
            try{
                chengben=holderInfo.getHolder_num()*Float.parseFloat(""+holderInfo.getCost_price());
                shizhi = holderInfo.getHolder_num()*Float.parseFloat(""+holderInfo.getNow_price());
            }catch (NumberFormatException e){}
            temItemMap.put("stockFund",""+shizhi);//市值
            temItemMap.put("income",""+(shizhi-chengben));//参考盈亏

            temItemMap.put("incomeRate",""+df.format(((shizhi-chengben)/shizhi*100))+"%");//参考盈亏率
            listDataMap.add(temItemMap);
        }

        AccountInfo accountInfo=accountMapper.queryAccountById(accountId);
        Map<String,Object> temMap=new HashMap<>();
        temMap.put("allIncome",""+ accountInfo.getTotal_income());//总盈亏


        temMap.put("allIncomeRate",""+ df.format(accountInfo.getTotal_income_rate()*100)+"%");//总收益率
        temMap.put("todayIncome",""+ accountInfo.getTotal_income());//今日盈亏
        temMap.put("canUseFund",""+ accountInfo.getCan_use_assets());//可用资产
        temMap.put("allFund",""+ accountInfo.getTotal_assets());//总资产
        temMap.put("holderFund",""+holderFund);

        map.put("listData",listDataMap);
        map.put("hodlerBaseInfo",temMap);
        return map;
    }

    //查询正在委托的
    @Override
    public List<EntrustStockInfo> queryMyEntrust(String accountId){
        List<EntrustStockInfo> list=entrustMapper.queryMyEntrustById(accountId);
        return list;
    }


    //查询委托历史
    @Override
    public List<Object> queryMyEntrustHistory(String accountId, String startDate, String endDate, int page){
        List<Object> listData=new ArrayList<>();

        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date temDate = sdf.parse(endDate);
            if(sdf.format(new Date()).equals(sdf.format(temDate))){//是今天
                List<EntrustStockInfo> listToday=entrustMapper.queryMyEntrustById(accountId);
                listData.addAll(listToday);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<EntrustStockInfoHistory> list=entrustMapper.queryMyEntrustHistoryById(accountId,startDate,endDate,page);
        listData.addAll(list);
        return listData;
    }

    //执行撤单
    @Override
    public boolean revokeMyEntrust(String accountId,String entrustId){
        EntrustStockInfo info=entrustMapper.queryMyEntrustOneById(entrustId);
        //删除委托
        entrustMapper.deleteEntrust(entrustId);
        //添加委托记录
        entrustMapper.insertEntrustHistory(info,"2");
        return true;
    }


    //购买股票
    @Override
    public int entrustBuyStock(String accountId,String stockCode,String stockCodeStr,String stockName,String entrustPrice,int count){
        //判断我是否有足够的钱购买
        AccountInfo acountInfo=accountMapper.queryAccountById(accountId);
        float canUse=0;
        try{
            canUse=Float.parseFloat(""+acountInfo.getCan_use_assets());
            float entrustPriceFloat=Float.parseFloat(""+entrustPrice);
            if(canUse<(entrustPriceFloat*count)){//钱不够
                return BaseController.ERROR_NO_MONEY;
            }
            canUse-=entrustPriceFloat*count;//从可用中减去
        }catch (NumberFormatException e){
            return BaseController.ERROR_NO_MONEY;
        }
        int status=entrustMapper.buyOrSellStock(accountId,stockCode,stockCodeStr,stockName,entrustPrice,"1",""+count);

        if(status==1){
            //成功后，需要从可用中减去
            accountMapper.chanageCanUseMoney(accountId,canUse+"");

            return BaseController.ERROR_CODE_SUCCESS;
        }else{
            return BaseController.ERROR_CODE_OTHER;
        }
    }
    //卖出股票
    @Override
    public int entrustSellStock(String accountId,String stockCode,String stockCodeStr,String stockName,String entrustPrice,int count){
        //判断我是否有足够的股票可以卖
        List<HolderInfo> myHolder=stockInfoMapper.queryMyHolderWithStock(accountId,stockCodeStr);
        if(myHolder==null||myHolder.size()<=0){//没有持仓
            return BaseController.ERROR_NO_HOLDER;
        }
        long holderCount=0;
        for(HolderInfo info:myHolder){
            holderCount+=info.getHolder_num();
        }
        if(holderCount<count){//持仓不够
            return BaseController.ERROR_NO_HOLDER;
        }

        int status=entrustMapper.buyOrSellStock(accountId,stockCode,stockCodeStr,stockName,entrustPrice,"2",""+count);
        if(status==1){
            return BaseController.ERROR_CODE_SUCCESS;
        }else{
            return BaseController.ERROR_CODE_OTHER;
        }
    }

}

























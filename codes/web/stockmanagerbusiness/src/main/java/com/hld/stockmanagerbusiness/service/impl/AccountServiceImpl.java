package com.hld.stockmanagerbusiness.service.impl;

import com.hld.stockmanagerbusiness.bean.AccountInfo;
import com.hld.stockmanagerbusiness.bean.EntrustStockInfo;
import com.hld.stockmanagerbusiness.bean.EntrustStockInfoHistory;
import com.hld.stockmanagerbusiness.bean.HolderInfo;
import com.hld.stockmanagerbusiness.controller.BaseController;
import com.hld.stockmanagerbusiness.mapper.AccountMapper;
import com.hld.stockmanagerbusiness.mapper.EntrustMapper;
import com.hld.stockmanagerbusiness.mapper.StockInfoMapper;
import com.hld.stockmanagerbusiness.mapper.UserMapper;
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
    @Autowired
    UserMapper userMapper;

    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public Map<String,Object> queryHolderInfos(String accountId){
        Map<String,Object> map=new HashMap<>();
        List<HolderInfo> listData = stockInfoMapper.queryMyHolderByAccountId(accountId);//拿到账户的所有持仓信息
        String entrustPriceStr=entrustMapper.queryEntrustPrice(accountId);
        float entrustPrice=0;
        try{entrustPrice=Float.parseFloat(entrustPriceStr+"");}catch (NumberFormatException e){}

        List<Map<String,Object>> listDataMap=new ArrayList<>();
        for(HolderInfo holderInfo:listData){
            float price=0;
            try{price=Float.parseFloat(""+holderInfo.getNow_price());}catch (NumberFormatException e){}

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
        if(accountInfo==null){
            return BaseController.getErrorMap(BaseController.ERROR_CODE_OTHER);
        }

        float allHolderAssets=0;//总持有市值
        float canUseAssets = 0;//可用资产
        float initAllAssets=0;//初始总资产

        try{allHolderAssets= Float.parseFloat(""+stockInfoMapper.queryUserAllValue(accountId));}catch (NumberFormatException e){}//总持有市值
        try{canUseAssets = Float.parseFloat(""+accountInfo.getCan_use_assets());}catch (NumberFormatException e){}//可用资产
        try{initAllAssets=Float.parseFloat(""+accountInfo.getInit_total_assets());}catch (NumberFormatException e){}//初始总资产


        float yestodayAllAssets=0;
        try{
            yestodayAllAssets=Float.parseFloat(""+accountMapper.queryYestodayTotalAssets(accountId));//昨日的总资产
        }catch (NumberFormatException e){}



        Map<String,Object> temMap=new HashMap<>();
        temMap.put("allIncome",""+ (canUseAssets+allHolderAssets+entrustPrice-initAllAssets));//总盈亏

        temMap.put("allIncomeRate",""+ df.format(((canUseAssets+allHolderAssets+entrustPrice-initAllAssets)/initAllAssets)*100)+"%");//总收益率
        if(yestodayAllAssets>0){
            temMap.put("todayIncome",""+(canUseAssets+allHolderAssets+entrustPrice-yestodayAllAssets));//今日盈亏
        }else{
            temMap.put("todayIncome","0");//今日盈亏
        }
        temMap.put("canUseFund",""+ accountInfo.getCan_use_assets());//可用资产
        temMap.put("allFund",""+ (canUseAssets+allHolderAssets+entrustPrice));//总资产
        temMap.put("holderFund",""+allHolderAssets);

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

    //查询表中所有的委托
    @Override
    public List<EntrustStockInfo> queryAllEntrust(){
        List<EntrustStockInfo> list=entrustMapper.queryAllEntrust();
        return list;
    }
    //购买股票
    @Override
    public void buyStock(EntrustStockInfo info,String buyPrice){

        info.setVol_num(Long.parseLong(info.getEntrust_num()));
        info.setVol_price(buyPrice);

        //增加历史
        entrustMapper.insertEntrustHistory(info,"1");

        //删除委托
        int count=entrustMapper.deleteEntrust(info.getId()+"");
        //委托删除成功
        if(count>0){
            //增加持仓
            float nowPrice=Float.parseFloat(buyPrice+"");
            List<HolderInfo> list=stockInfoMapper.queryMyHolderWithStock(info.getAccount_id()+"",""+info.getStock_code_str());
            if(list!=null&&list.size()>0){//有值,需要修改
                HolderInfo item=list.get(0);
                float costPrice=Float.parseFloat(item.getCost_price());
                long holderNum=item.getHolder_num();
                long nowNum= Long.parseLong(info.getEntrust_num())+holderNum;
                //计算新成本
                float cb=(nowPrice*nowNum-nowPrice*holderNum+costPrice*holderNum)/nowNum;
                stockInfoMapper.updateHolder(nowNum+"",cb+"",item.getId()+"");

                if(list.size()==1){//只有一条数据
                }else{//有多条数据，需要重新插入
                }
            }else{//没有，直接增加持仓
                HolderInfo bean=new HolderInfo();
                bean.setUser_id(info.getUser_id());
                bean.setAccount_id(info.getAccount_id());
                bean.setStock_code(""+info.getStock_code());
                bean.setStock_code_str(""+info.getStock_code_str());
                bean.setStock_name(""+info.getStock_name());
                bean.setCost_price(buyPrice);
                bean.setNow_price(buyPrice);
                bean.setHolder_num(Long.parseLong(info.getEntrust_num()));
                bean.setUsable_num(0);

                stockInfoMapper.addHolder(bean);
            }
        }
    }
    //卖出股票
    @Override
    public void sellStock(EntrustStockInfo info,String sellPrice){
        info.setVol_num(Long.parseLong(info.getEntrust_num()));
        info.setVol_price(sellPrice);

        //增加历史
        entrustMapper.insertEntrustHistory(info,"1");

        //删除委托
        int count=entrustMapper.deleteEntrust(info.getId()+"");
        float nowPrice=Float.parseFloat(sellPrice+"");
        //减掉持仓
        //委托删除成功
        if(count>0){
            //将钱加到账户
            AccountInfo acountInfo=accountMapper.queryAccountById(""+info.getAccount_id());
            float canUse=Float.parseFloat(""+acountInfo.getCan_use_assets());
            float price=Float.parseFloat(info.getEntrust_price());
            long num=Long.parseLong(info.getEntrust_num());
            canUse+=price*num;
            accountMapper.chanageCanUseMoney(""+info.getAccount_id(),canUse+"");


            //增加持仓
            List<HolderInfo> list=stockInfoMapper.queryMyHolderWithStock(info.getAccount_id()+"",info.getStock_code_str());
            if(list!=null&&list.size()>0){//有值,需要修改
                long volNum=Long.parseLong(info.getEntrust_num());

                for(int i=0;i<list.size();i++){
                    HolderInfo item=list.get(i);
                    float costPrice=Float.parseFloat(item.getCost_price());
                    long holderNum=item.getHolder_num();
                    long nowNum= holderNum-volNum;
                    if(nowNum<0){
                        volNum=-nowNum;
                        nowNum=0;
                    }
                    if(nowNum==0){
                        //删除这条持仓
                        stockInfoMapper.deleteHolder(item.getId()+"");
                        break;
                    }
                    //计算新成本
                    float cb=(nowPrice*nowNum-nowPrice*holderNum+costPrice*holderNum)/nowNum;
                    stockInfoMapper.updateHolder(nowNum+"",cb+"",item.getId()+"");
                }
            }
        }
    }


    //查询委托历史
    @Override
    public List<Object> queryMyEntrustHistory(String accountId, String startDate, String endDate, int page,int indexType){
        List<Object> listData=new ArrayList<>();
        if(indexType!=0){//查询所有委托
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
        }

        List<EntrustStockInfoHistory> list=null;
        if(indexType==0){//只查询成交
            list=entrustMapper.queryMyEntrustHistoryById2(accountId,startDate,endDate,page,"1");
        }else{
            list=entrustMapper.queryMyEntrustHistoryById(accountId,startDate,endDate,page);
        }

        listData.addAll(list);
        return listData;
    }

    //执行撤单
    @Override
    public boolean revokeMyEntrust(String entrustId){
        EntrustStockInfo info=entrustMapper.queryMyEntrustOneById(entrustId);
        //删除委托
        int count=entrustMapper.deleteEntrust(entrustId);
        if(count>0){//删除成功
            //添加委托记录
            entrustMapper.insertEntrustHistory(info,"2");
            //将钱加回去
            AccountInfo acountInfo=accountMapper.queryAccountById(""+info.getAccount_id());
            float canUse=Float.parseFloat(""+acountInfo.getCan_use_assets());
            float price=Float.parseFloat(info.getEntrust_price());
            long num=Long.parseLong(info.getEntrust_num());
            canUse+=price*num;
            accountMapper.chanageCanUseMoney(""+info.getAccount_id(),canUse+"");
        }
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
        entrustMapper.buyOrSellStock(accountId,stockCode,stockCodeStr,stockName,entrustPrice,"1",""+count);

        accountMapper.chanageCanUseMoney(accountId,canUse+"");

        return BaseController.ERROR_CODE_SUCCESS;

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
        long canUseCount=0;
        for(HolderInfo info:myHolder){
            holderCount+=info.getHolder_num();
            canUseCount+=info.getUsable_num();
        }
        if(canUseCount<count){//持仓不够
            return BaseController.ERROR_NO_HOLDER;
        }
        if(holderCount<count){//持仓不够
            return BaseController.ERROR_NO_HOLDER;
        }

        entrustMapper.buyOrSellStock(accountId,stockCode,stockCodeStr,stockName,entrustPrice,"2",""+count);
        return BaseController.ERROR_CODE_SUCCESS;

    }


    //模糊查询
    @Override
    public List<Map<String,Object>> queryStockFuzzy(String searchStr){
        List<Map<String,Object>> listData=stockInfoMapper.queryStockFuzzy(searchStr);
        return listData;
    }

    @Override
    public AccountInfo queryDefAccountInfo(String userId) {
        String accountId=userMapper.queryDefAccountId(userId);
        return accountMapper.queryAccountById(accountId);
    }


    @Override
    public void addAccountToHis(){
        accountMapper.addAccountToHis();
    }



}

























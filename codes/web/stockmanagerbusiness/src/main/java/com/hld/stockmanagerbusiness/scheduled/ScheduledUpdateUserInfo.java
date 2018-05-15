package com.hld.stockmanagerbusiness.scheduled;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.hld.stockmanagerbusiness.bean.AccountInfo;
import com.hld.stockmanagerbusiness.bean.EntrustStockInfo;
import com.hld.stockmanagerbusiness.bean.HolderInfo;
import com.hld.stockmanagerbusiness.mapper.AccountMapper;
import com.hld.stockmanagerbusiness.mapper.MathMapper;
import com.hld.stockmanagerbusiness.mapper.StockInfoMapper;
import com.hld.stockmanagerbusiness.service.AccountService;
import com.hld.stockmanagerbusiness.utils.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//对股票,持仓，市值进行更新
@Component
public class ScheduledUpdateUserInfo {
    private Logger logger = Logger.getLogger(ScheduledUpdateUserInfo.class);

    SimpleDateFormat monthFormat=new SimpleDateFormat("yyyy-M");
    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-M-d");
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    AccountService accountService;
    @Autowired
    StockInfoMapper stockInfoMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    MathMapper matchMapper;

    private boolean todayIsRest=true;//是否休息，默认休息

    public ScheduledUpdateUserInfo(){//构造函数
        updateAllUserInfo();
    }


    //每日的23:10将账户添加进历史
    @Scheduled(cron="0 10 23 ? * *")
    public void addAccountToHis() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("跑批开始时间:"+sdf.format(new Date()));
        //更新我的收益，收益率(验算一遍)
        List<Long> listAllId = stockInfoMapper.queryAllAccountId();
        for(long itemId:listAllId){

            int count7=accountMapper.queryVolCount(itemId+"",7);
            int count30=accountMapper.queryVolCount(itemId+"",30);
            int countAll=accountMapper.queryVolAllCount(itemId+"");
            accountMapper.updateAccountVolCount(count7+"",count30+"",countAll+"");

            AccountInfo info=accountMapper.queryAccountById(itemId+"");
            float allMarketVal=0;
            //总市值
            try{allMarketVal=Float.parseFloat(""+stockInfoMapper.queryUserAllValue(itemId+""));}catch (NumberFormatException e){}


            String total7Str=accountMapper.queryHisTotalAssets(""+itemId,"7");
            String total30Str=accountMapper.queryHisTotalAssets(""+itemId,"30");

            float total7=0;//一周前的总资产
            float total30=0;//一个月前的总资产

            float canUse=0;//可用资金
            float initTotalAssets=0;//初始化总资产


            try{total7=Float.parseFloat(""+total7Str);}catch (NumberFormatException e){}
            try{total30=Float.parseFloat(""+total30Str);}catch (NumberFormatException e){}
            try{canUse=Float.parseFloat(""+info.getCan_use_assets());}catch (NumberFormatException e){}
            try{initTotalAssets=Float.parseFloat(info.getInit_total_assets());}catch (NumberFormatException e){}
            if(initTotalAssets>0){
                float allIncome=allMarketVal+canUse-initTotalAssets;//总收入
                float allIncomeRate=allIncome/initTotalAssets;//总收益率
                float income7=0;//一周收益
                float income7Rate=0;//一周收益率
                float income30=0;//一个月收益
                float income30Rate=0;//一个月收益率
                if(total7<=0){
                    total7=initTotalAssets;
                }
                if(total30<=0){
                    total30=initTotalAssets;
                }


                if(total7>0){
                    income7=allMarketVal+canUse-total7;
                    income7Rate=income7/total7;
                }
                if(total30>0){
                    income30=allMarketVal+canUse-total30;
                    income30Rate=total30/total30;
                }
                accountMapper.updateAccountIncome(itemId+"",(allMarketVal+canUse)+"",allIncome+"",allIncomeRate+"",income7+"",income7Rate+"",income30+"",income30Rate+"");
            }
        }
        System.out.println("执行完成:更新完成收益率!");
        //更新所有比赛内用户排名
        List<Long> listMatchIds=matchMapper.queryAllMatchId();
        for(Long matchId:listMatchIds){
            List<Integer> listUserIds= accountMapper.queryRanking(""+matchId);
            for(int i=0;i<listUserIds.size();i++){
                int accountId=listUserIds.get(i);
                accountMapper.updateUserRanking(""+accountId,""+(i+1));
            }
        }
        System.out.println("执行完成:更新完成用户排名!");

        //将今天的账户添加进去
        accountService.addAccountToHis();
        System.out.println("执行完成:将今天的账户添加进历史!");

        //更新所有人的交易次数
        accountMapper.updateAllDealNum();
        System.out.println("执行完成:更新所有人的交易次数!");
        System.out.println("执行结束时间:"+sdf.format(new Date()));
    }

    //每日凌晨1:10分进行更新所有人的信息
    @Scheduled(cron="0 10 01 ? * *")
    public void updateAllUserInfo() {
        //检查今天是否是休息
        Calendar calendar=Calendar.getInstance();
        int weekNum=calendar.get(Calendar.DAY_OF_WEEK);
        todayIsRest=false;//不休息
        if(weekNum==Calendar.SATURDAY||weekNum==Calendar.SUNDAY){//周六或周日 肯定不开盘
            todayIsRest=true;//今天不开盘
        }
        String request=HttpUtil.sendPost("http://v.juhe.cn/calendar/month?year-month="+monthFormat.format(calendar.getTime())+"&key=12f6689fac20817e25c1c5bf16766f79");
        if(request.contains(dateFormat.format(calendar.getTime()))){//假期列表中有今天，今天休息
            todayIsRest=true;//今天不开盘
        }
    }


    //每日凌晨00:01分将今天所有的未处理的委托都撤单
    //将持仓中的可用数量改为全部数量
    @Scheduled(cron="0 01 00 ? * *")
    public void chanageEntrustInfo(){
        List<EntrustStockInfo> listAll=accountService.queryAllEntrust();
        for(EntrustStockInfo item:listAll){
            accountService.revokeMyEntrust(""+item.getId());//执行撤单
        }
        //将可用更高为总持仓
        stockInfoMapper.updateHolderCanUse();
    }


    //每过20秒 更新所有人持仓的现价
    @Scheduled(cron="0/20 * * * * ?")
    public void scranHolder(){
        if(!isDoEntrust(false)){//非交易时间
            return;
        }
        List<String> listData = stockInfoMapper.queryAllHolderStock();
        for(String item:listData){
            String jsonStr=HttpUtil.sendPost("http://47.100.180.170:8080/stockServer/queryStockInfoByCode?stockCode="+item);
            try{
                JSONObject json=JSON.parseObject(jsonStr);
                String tradingStatus=json.getString("tradingStatus");
                if("TRADING_STATUS_NORMAL".equals(tradingStatus)){//正常状态
                    String lastPx=json.getString("lastPx");
                    stockInfoMapper.updateHolderNowPrice(item,lastPx);
                }

            }catch (JSONException e){
//                e.printStackTrace();
            }
        }
    }

    //每秒查询委托列表是否可以成交
    @Scheduled(cron="0/1 * * * * ?")
    public void scranEntrust(){
        if(!isDoEntrust(true)){//非交易时间
            return;
        }
        List<EntrustStockInfo> listAll=accountService.queryAllEntrust();
        for(EntrustStockInfo item:listAll){

            String jsonStr=HttpUtil.sendPost("http://47.100.180.170:8080/stockServer/queryStockInfoByCode?stockCode="+item.getStock_code_str());
            try{
                JSONObject json=JSON.parseObject(jsonStr);
                String tradingStatus=json.getString("tradingStatus");
                if("TRADING_STATUS_NORMAL".equals(tradingStatus)){//正常状态
                    JSONArray sellArr=json.getJSONArray("sell");
                    JSONArray buyArr=json.getJSONArray("buy");
                    if(item.getType()==1&&sellArr!=null&&sellArr.size()>=5){//买入,且有人卖
//                    item.getEntrust_price()
                        float entrustPrice=Float.parseFloat(item.getEntrust_price()+"");
                        float tradePx=Float.parseFloat(sellArr.getJSONObject(0).getString("tradePx"));
                        if(tradePx>0&&entrustPrice>=tradePx){//可以成交,按照卖一价购买
//                            item.getEntrust_num()//委托数量
                            accountService.buyStock(item,tradePx+"");
//                            System.out.println("买入了"+item.getId()+"......"+item.getStock_name());
                        }else{
//                            System.out.println("不买:"+item.getStock_name()+"  委托价:"+entrustPrice+"   卖一价:"+tradePx);
                        }
                    }else if(item.getType()==2&&buyArr!=null&&buyArr.size()>=5){//卖出,且有人买
                        float entrustPrice=Float.parseFloat(item.getEntrust_price()+"");
                        float tradePx=Float.parseFloat(buyArr.getJSONObject(0).getString("tradePx"));
                        if(tradePx>0&&entrustPrice>=tradePx){//可以成交,按照买一价卖出
//                            item.getEntrust_num()//委托数量
                            accountService.sellStock(item,tradePx+"");
//                            System.out.println("卖出========"+item.getStock_name());
                        }
                    }
                }

            }catch (JSONException e){
//                e.printStackTrace();
            }
        }
    }










    //判断是否处于休息时间
    private boolean isDoEntrust(boolean isEntrust){
        if(todayIsRest){//休息
            return false;
        }

        Date now=Calendar.getInstance().getTime();
        try {
            String nowDate=dateFormat.format(now);
            Date date1 = format.parse(nowDate+" 09:30:02");
            Date date2 = format.parse(nowDate+" 11:29:58");

            Date date3 = format.parse(nowDate+" 13:00:02");
            Date date4 = format.parse(nowDate+" 14:59:58");

            if(!isEntrust){
                date1 = format.parse(nowDate+" 09:28:02");
                date2 = format.parse(nowDate+" 11:30:58");

                date3 = format.parse(nowDate+" 13:00:02");
                date4 = format.parse(nowDate+" 15:00:58");
            }
//            Date date4 = format.parse(nowDate+" 19:59:58");

            long nowTime=now.getTime();

//            System.out.println("date1:"+format.format(date1)+"   now:"+format.format(now));
            if((nowTime>date1.getTime()&&nowTime<date2.getTime())||(nowTime>date3.getTime()&&nowTime<date4.getTime())){
//                System.out.println("可以交易");
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}

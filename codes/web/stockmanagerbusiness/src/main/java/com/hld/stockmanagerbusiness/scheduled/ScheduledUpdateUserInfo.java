package com.hld.stockmanagerbusiness.scheduled;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.hld.stockmanagerbusiness.bean.EntrustStockInfo;
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

@Component
public class ScheduledUpdateUserInfo {
    private Logger logger = Logger.getLogger(ScheduledUpdateUserInfo.class);

    SimpleDateFormat monthFormat=new SimpleDateFormat("yyyy-M");
    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-M-d");
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    AccountService accountService;

    private boolean todayIsRest=true;//是否休息，默认休息

    //每日凌晨1:10分进行更新所有人的信息
    @Scheduled(cron="0 10 01 ? * *")
    public void updateAllUserInfo() {
        //检查今天是否是休息
        Calendar calendar=Calendar.getInstance();
        int weekNum=calendar.get(Calendar.DAY_OF_WEEK);
        if(weekNum==Calendar.SATURDAY||weekNum==Calendar.SUNDAY){//周六或周日 肯定不开盘
            todayIsRest=true;//今天不开盘
        }
        String request=HttpUtil.sendPost("http://v.juhe.cn/calendar/month?year-month="+monthFormat.format(calendar.getTime())+"&key=12f6689fac20817e25c1c5bf16766f79");
        if(request.contains(dateFormat.format(calendar.getTime()))){//假期列表中有今天，今天休息
            todayIsRest=true;//今天不开盘
        }
        //更新我的收益，收益率(验算一遍)

    }


    //每日凌晨00:01分将今天所有的未处理的委托都撤单
    @Scheduled(cron="0 01 00 ? * *")
    public void chanageEntrustInfo(){
        List<EntrustStockInfo> listAll=accountService.queryAllEntrust();
        for(EntrustStockInfo item:listAll){
            accountService.revokeMyEntrust(""+item.getId());//执行撤单
        }
    }



    //每秒执行查询委托
    @Scheduled(cron="0/1 * * * * ?")
    public void scranEntrust(){
        if(!isDoEntrust()){//非交易时间
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
//                            System.out.println("买入了"+item.getId()+"......"+item.getStock_name()+"  委托价:"+entrustPrice+"   卖一价:"+tradePx);
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










    //判断是否执行委托
    private boolean isDoEntrust(){
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
//            Date date4 = format.parse(nowDate+" 19:59:58");

            long nowTime=now.getTime();

            System.out.println("date1:"+format.format(date1)+"   now:"+format.format(now));
            if((nowTime>date1.getTime()&&nowTime<date2.getTime())||(nowTime>date3.getTime()&&nowTime<date4.getTime())){
                System.out.println("可以交易");
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}

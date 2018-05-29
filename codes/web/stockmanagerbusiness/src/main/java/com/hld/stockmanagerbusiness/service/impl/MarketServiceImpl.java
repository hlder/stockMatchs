package com.hld.stockmanagerbusiness.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hld.stockmanagerbusiness.service.MarketService;
import com.hld.stockmanagerbusiness.service.RedisService;
import com.hld.stockmanagerbusiness.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class MarketServiceImpl implements MarketService {
    @Autowired
    RedisService redisService;

    @Override
    public String loadMarketIndex(HttpServletRequest request) {
        String redData=redisService.get("loadMarketIndex");


        String data="";
        long nowTime=Calendar.getInstance().getTimeInMillis();
        if(redData!=null&&!"".equals(redData)){
            JSONObject jo = JSON.parseObject(redData);
            long time=Long.parseLong(jo.getString("time"));
            if((time-nowTime)<10000){//超过10秒钟，重新获取
                data=jo.getString("data");
            }
        }

        if(data==null||"".equals(data)){//redis没有数据
            data=HttpUtil.sendPost("https://hq.sinajs.cn/list=sh000001,sz399001,sz399006,sz399300,sh000016,sz399905","","gbk");
            Map<String,Object> map=new HashMap<>();
            map.put("time",nowTime);
            map.put("data",data);
            redisService.set("loadMarketIndex",JSON.toJSONString(map));
        }

        return data;
    }
    @Override
    public String queryStockRankUp(HttpServletRequest request) {
        String redData=redisService.get("queryStockRankUp");


        String data="";
        long nowTime=Calendar.getInstance().getTimeInMillis();
        if(redData!=null&&!"".equals(redData)){
            JSONObject jo = JSON.parseObject(redData);
            long time=Long.parseLong(jo.getString("time"));
            if((time-nowTime)<10000){//超过10秒钟，重新获取
                data=jo.getString("data");
            }
        }

        if(data==null||"".equals(data)){//redis没有数据
            data=HttpUtil.sendPost("http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=10&sort=changepercent&asc=0&node=hs_a&symbol=&_s_r_a=init#","","gbk");

            Map<String,Object> map=new HashMap<>();
            map.put("time",nowTime);
            map.put("data",data);
            redisService.set("loadMarketIndex",JSON.toJSONString(map));
        }

        return data;
    }

    @Override
    public String queryStockRankDown(HttpServletRequest request) {
        String redData=redisService.get("queryStockRankDown");

        String data="";
        long nowTime=Calendar.getInstance().getTimeInMillis();
        if(redData!=null&&!"".equals(redData)){
            JSONObject jo = JSON.parseObject(redData);
            long time=Long.parseLong(jo.getString("time"));
            if((time-nowTime)<10000){//超过10秒钟，重新获取
                data=jo.getString("data");
            }
        }

        if(data==null||"".equals(data)){//redis没有数据
            data=HttpUtil.sendPost("http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=10&sort=changepercent&asc=1&node=hs_a&symbol=&_s_r_a=sort#","","gbk");

            Map<String,Object> map=new HashMap<>();
            map.put("time",nowTime);
            map.put("data",data);
            redisService.set("loadMarketIndex",JSON.toJSONString(map));
        }

        return data;
    }



    @Override
    public String queryStockNameByCodes(HttpServletRequest request) {
        return null;
    }

    public static void main(String []args){
        String str=HttpUtil.sendPost("https://hq.sinajs.cn/list=sh000001,sz399001,sz399006,sz399300,sh000016,sz399905","","gbk");
//        req=new String(req.get,"utf-8");
        System.out.println("转换前:"+str);

    }


}

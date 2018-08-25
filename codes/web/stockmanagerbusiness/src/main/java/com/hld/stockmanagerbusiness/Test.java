package com.hld.stockmanagerbusiness;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hld.stockmanagerbusiness.utils.HttpUtil;

import java.lang.Math;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2018/5/10.
 */
public class Test {
//    https://admin.lhyone.com/ttpoker/auth/nn/createRoom?userId=10000&token=IzN%2BSWIkFhHisGqAaTx%2F%2BvonKWiiRX0bs%2BikFeEfZJQEq4mVmJWiLHtZIlxf%2F7IWohwhsA8oEv779yn8JdHb7HslX%2FDNExiFIDhmP4O1lrhu9ZBIdfR%2BEdsLqthTf4aRp7i5TfYsNvIq9U7qWC%2B4HneBZVh9IsBBkYG3xDQhIUw%3D

    public static String baseUrl="http://47.93.10.166/ttPoker";
//    public static String baseUrl="http://192.168.1.249:8099";

    public static String token="mhCsxXcgj0kdfLrncAHC%252FRw7VL0pxktJBMz4EgLRbvNp7BgfxlvTGE28pXqemF5adjZWY8XeKxoKX7UuKgwwRZgkTTuzT2VHYJRcVOMdf2ebqEt5wPJRW%252BtL9pExTOYldiDiHjPphtyNVEGxzFNwiHeBZVh9IsBBkYG3xDQhIUw%253D";
    public static String userId = "10000";
    public static String baseParams =  "userId="+userId+"&token="+token;
    public static void main(String [] args){
        doStart();
    }

    public static void doStart(){
        long count = 0;
        while(true){
            if(count%1000 == 0){
                checkLogin();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            checkNNRoom();
            checkMjRoom();
            count++;
        }
    }


    public static void checkLogin(){
        String json = "{\"country\":\"CN\", \"province\":\"Shanghai\", " +

                "\"unionid\":\"oHRAHuPxOvqCdVfgIS51Pxy_Mt0w1525790822478.36\", " +
                "\"headimgurl\":\"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3468481793,3455309356&fm=27&gp=0.jpg\", " +
                "\"nickname\":\"深海之鱼却厌水\", " +

//				"\"unionid\":\"oHRAHuPxOvqCdVfgIS51Pxy_Mt0w1526692738700.49\", " +
//				"\"headimgurl\":\"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526975016194&di=e5587879d2c775b1f6c020740f1c6fc7&imgtype=0&src=http%3A%2F%2Fs8.sinaimg.cn%2Fmw690%2F0032NRYFzy77UApfKiX67%26690\", " +
//				"\"nickname\":\"明天会更好\", " +

                "\"openid\":\"o3LILj5F3ap9hEKAOogYtdOO-qf8\", " +
                "\"city\":\"\", \"sex\":1, \"language\":\"zh_CN\", \"privilege\":[]}";

        JSONObject result = JSON.parseObject(json);

        String params ="country="+ result.getString("country")+"&"+
                "province="+  result.getString("province")+"&"+
                "headimgurl="+  result.getString("headimgurl")+"&"+
                "unionid="+  result.getString("unionid")+"&"+
                "openid="+ result.getString("openid")+"&"+
                "nickname="+ result.getString("nickname")+"&"+
                "city="+ result.getString("city")+"&"+
                "sex="+  result.getString("sex")+"&"+
                "language="+ result.getString("language")+"&"+
                "privilege="+  result.getString("privilege");

        System.out.println("params:"+params);

        try{
            String urlDoLogin=baseUrl+"/gold/wxAppLogin";
//            urlDoLogin = "http://www.baidu.com";
            String req= HttpUtil.sendPost(urlDoLogin,params);
            System.out.println(req);
            JSONObject jo= JSON.parseObject(req);
            JSONObject ja= jo.getJSONObject("data");

            if(ja!=null ){

                token = ja.getString("token");
                token =  URLEncoder.encode(token, "GBK");
                userId = ja.getString("userId");
                System.out.println("token:"+token);
                System.out.println("userId:"+userId);
                baseParams = "userId="+userId+"&token="+token;
                System.out.println("baseParams:"+baseParams);
            }
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public static void checkNNRoom(){
        try{
            String queryNNRoomList=baseUrl+"/gold/queryNNRoomList";
            String req= HttpUtil.sendPost(queryNNRoomList,baseParams);
            JSONObject jo= JSON.parseObject(req);
            JSONArray ja= jo.getJSONArray("data");

            int allNoneRoom=0;
            if(ja!=null&&ja.size()>0){
                for(int i=0;i<ja.size();i++){
                    JSONObject item=ja.getJSONObject(i);
                    long count= item.getLong("now_person_count");
                    if(count<=0){
                        allNoneRoom++;
                    }
                }
            }
            if(allNoneRoom<5){//空房间少于4个,则创建一个房间
                double random=Math.random()*100+1;
                createNNRoom(random);
            }

//            if(ja==null||ja.size()<4){
//                createNNRoom();
//            }
        }catch (Exception e){
            e.printStackTrace();

        }

    }
    public static void checkMjRoom(){
        try{
            String queryNNRoomList=baseUrl+"/gold/queryMjRoomList";
            String req=HttpUtil.sendPost(queryNNRoomList,baseParams);
            JSONObject jo= JSON.parseObject(req);
            JSONArray ja= jo.getJSONArray("data");

            int allNoneRoom=0;
            if(ja!=null&&ja.size()>0){
                for(int i=0;i<ja.size();i++){
                    JSONObject item=ja.getJSONObject(i);
                    long count= item.getLong("now_person_count");
                    if(count<=0){
                        allNoneRoom++;
                    }
                }
            }
            if(allNoneRoom<5){//空房间少于4个,则创建一个房间
                double random=Math.random()*100+1;
                createMjRoom(random);

            }
//            if(ja==null||ja.size()<5){
//                createMjRoom();
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    //创建一个牛牛房间
    public static void createNNRoom(double random){
        String params;
        if(random <= 60){
            params = "serverId=1&baseGold=50&inLimitGold=1500&outLimitGold=1500&roomDoubleRule=14,15,17,&isShow=1&gameType=1&" +
                    baseParams;
        }else if(random <= 80){
            params = "isShow=1&serverId=3&baseGold=100&inLimitGold=3000&outLimitGold=3000&gameRule=1&gameType=2&roomDoubleRule=14,15,17,&" +
                    baseParams;
        }else {
            params = "isShow=1&serverId=3&baseGold=200&inLimitGold=6000&outLimitGold=6000&gameRule=1&gameType=2&roomDoubleRule=14,15,17,&" +
                    baseParams;
        }
        String url=baseUrl+"/auth/nn/createRoom";
        String req=HttpUtil.sendPost(url,params);

        System.out.println("创建一个牛牛房间:"+random+"-->"+req);
    }
    //创建一个焖鸡房间
    public static void createMjRoom(double random){
        String params;
        if(random <= 60){
            params = "isShow=1&serverId=3&baseGold=50&inLimitGold=500&outLimitGold=250&gameRule=1&gameType=2&" +
                    baseParams;
        }else if(random <= 80){
            params = "isShow=1&serverId=3&baseGold=100&inLimitGold=1000&outLimitGold=500&gameRule=1&gameType=2&" +
                    baseParams;
        }else {
            params = "isShow=1&serverId=3&baseGold=200&inLimitGold=2000&outLimitGold=1000&gameRule=1&gameType=2&" +
                    baseParams;
        }
        String url=baseUrl+"/auth/jf/createRoom";
        String req=HttpUtil.sendPost(url,params);

        System.out.println("创建一个焖鸡房间:"+random+"-->"+req);
    }
}

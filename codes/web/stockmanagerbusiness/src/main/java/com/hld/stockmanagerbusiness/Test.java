package com.hld.stockmanagerbusiness;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hld.stockmanagerbusiness.utils.HttpUtil;

/**
 * Created by Administrator on 2018/5/10.
 */
public class Test {
//    https://admin.lhyone.com/ttpoker/auth/nn/createRoom?userId=10000&token=IzN%2BSWIkFhHisGqAaTx%2F%2BvonKWiiRX0bs%2BikFeEfZJQEq4mVmJWiLHtZIlxf%2F7IWohwhsA8oEv779yn8JdHb7HslX%2FDNExiFIDhmP4O1lrhu9ZBIdfR%2BEdsLqthTf4aRp7i5TfYsNvIq9U7qWC%2B4HneBZVh9IsBBkYG3xDQhIUw%3D


    public static String token="nomWvdR5sxvEdFOo%252FOh%252Ff2e5FmQzDSIfWAX4DGxYwtw9MWbwuD3NxG3WQVrlemMfohwhsA8oEv779yn8JdHb7BZYAl2OCV4Ppo4A88MLxeLI4n261r%252Fm9JEp4Tv%252BTBEgn%252F9vQ5GhyBZUGTxLpvY8uneBZVh9IsBBkYG3xDQhIUw%253D";

    public static void main(String [] args){
        while(true){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            checkNNRoom();
            checkMjRoom();
        }
    }

    public static void checkNNRoom(){
        try{
            String queryNNRoomList="https://admin.lhyone.com/ttpoker/gold/queryNNRoomList";
            String req= HttpUtil.sendPost(queryNNRoomList,"userId=10000&token="+token);
            JSONObject jo= JSON.parseObject(req);
            JSONArray ja= jo.getJSONArray("data");
            if(ja==null||ja.size()<4){
                createNNRoom();
            }
        }catch (Exception e){
            e.printStackTrace();

        }

    }
    public static void checkMjRoom(){
        try{
            String queryNNRoomList="https://admin.lhyone.com/ttpoker/gold/queryMjRoomList";
            String req=HttpUtil.sendPost(queryNNRoomList,"userId=10000&token="+token);
            JSONObject jo= JSON.parseObject(req);
            JSONArray ja= jo.getJSONArray("data");
            if(ja==null||ja.size()<5){
                createMjRoom();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    //创建一个牛牛房间
    public static void createNNRoom(){
        String url="https://admin.lhyone.com/ttpoker/auth/nn/createRoom";
        String req=HttpUtil.sendPost(url,"serverId=1&baseGold=50&inLimitGold=1500&outLimitGold=1500&roomDoubleRule=14,15,17,&isShow=1&" +
                "userId=10000&token="+token);

        System.out.println("创建一个牛牛房间"+req);
    }
    //创建一个焖鸡房间
    public static void createMjRoom(){
        String url="https://admin.lhyone.com/ttpoker/auth/jf/createRoom";
        String req=HttpUtil.sendPost(url,"isShow=1&serverId=3&baseGold=50&inLimitGold=500&outLimitGold=250&gameRule=1&gameType=2&" +
                "userId=10000&token="+token);

        System.out.println("创建一个焖鸡房间:"+req);
    }
}

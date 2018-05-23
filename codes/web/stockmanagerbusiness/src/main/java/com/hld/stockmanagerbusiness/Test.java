package com.hld.stockmanagerbusiness;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hld.stockmanagerbusiness.utils.HttpUtil;

public class Test {

//    private static String baseUrl="https://admin.lhyone.com";
    private static String baseUrl="http://192.168.1.249:8099";

    public static void main(String [] args){
        while(true){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
//            checkNNRoom();
            checkMjRoom();
        }
    }

    public static void checkNNRoom(){
        String queryNNRoomList=baseUrl+"/ttpoker/gold/queryNNRoomList";
        String req=HttpUtil.sendPost(queryNNRoomList,"userId=10000&token=HV5jhhvZA2TGSBGcnxXg5jQ%2BkE6Atxh0k96EP0S%2FzxHF752LjLZZbqxtx67lUFuh2u9AiUZVz3t4GRJIcyVrZgmJQTTee7ri8tH5ImkEKLJGqdBjBGVBDxvyxAy7x8mRNyKI67YngF7mYpGvKXgSa3eBZVh9IsBBkYG3xDQhIUw%3D");
        JSONObject jo= JSON.parseObject(req);
        JSONArray ja= jo.getJSONArray("data");
        if(ja==null||ja.size()<2){
            createNNRoom();
        }
    }
    public static void checkMjRoom(){
        String queryNNRoomList=baseUrl+"/ttpoker/gold/queryMjRoomList";
        String req=HttpUtil.sendPost(queryNNRoomList,"userId=10000&token=HV5jhhvZA2TGSBGcnxXg5jQ%2BkE6Atxh0k96EP0S%2FzxHF752LjLZZbqxtx67lUFuh2u9AiUZVz3t4GRJIcyVrZgmJQTTee7ri8tH5ImkEKLJGqdBjBGVBDxvyxAy7x8mRNyKI67YngF7mYpGvKXgSa3eBZVh9IsBBkYG3xDQhIUw%3D");
        JSONObject jo= JSON.parseObject(req);
        JSONArray ja= jo.getJSONArray("data");
        if(ja==null||ja.size()<1){
            createMjRoom();
        }
    }




    //创建一个牛牛房间
    public static void createNNRoom(){
        System.out.println("创建一个牛牛房间");
        String url=baseUrl+"/ttpoker/auth/nn/createRoom";
        HttpUtil.sendPost(url,"serverId=1&baseGold=50&inLimitGold=1500&outLimitGold=1500&roomDoubleRule=14,15,17,&isShow=1&" +
                "userId=10000&token=o4jftkISctd525E3qZkIaTyt5dfxWpBo4NpjYxXTVxDkandQ%252F%252FLZvlzNfUB0zbhK2u9AiUZVz3t4GRJIcyVrZiLeWueKjYQ%252BnWbcqOlSqnPOVaiAxPVCPHNuEhYN9V8YrCAesi7JULFMzkzWV5fKhXeBZVh9IsBBkYG3xDQhIUw%253D&oq=o4jftkISctd525E3qZkIaTyt5dfxWpBo4NpjYxXTVxDkandQ%252F%252FLZvlzNfUB0zbhK2u9AiUZVz3t4GRJIcyVrZiLeWueKjYQ%252BnWbcqOlSqnPOVaiAxPVCPHNuEhYN9V8YrCAesi7JULFMzkzWV5fKhXeBZVh9IsBBkYG3xDQhIUw%253D");
    }
    //创建一个焖鸡房间
    public static void createMjRoom(){
        System.out.println("创建一个焖鸡房间");
        String url=baseUrl+"/ttpoker/auth/jf/createRoom";
        HttpUtil.sendPost(url,"isShow=1&serverId=3&baseGold=50&inLimitGold=500&outLimitGold=250&gameRule=1&gameType=2&" +
                "userId=10000&token=o4jftkISctd525E3qZkIaTyt5dfxWpBo4NpjYxXTVxDkandQ%252F%252FLZvlzNfUB0zbhK2u9AiUZVz3t4GRJIcyVrZiLeWueKjYQ%252BnWbcqOlSqnPOVaiAxPVCPHNuEhYN9V8YrCAesi7JULFMzkzWV5fKhXeBZVh9IsBBkYG3xDQhIUw%253D&oq=o4jftkISctd525E3qZkIaTyt5dfxWpBo4NpjYxXTVxDkandQ%252F%252FLZvlzNfUB0zbhK2u9AiUZVz3t4GRJIcyVrZiLeWueKjYQ%252BnWbcqOlSqnPOVaiAxPVCPHNuEhYN9V8YrCAesi7JULFMzkzWV5fKhXeBZVh9IsBBkYG3xDQhIUw%253D");

    }

}

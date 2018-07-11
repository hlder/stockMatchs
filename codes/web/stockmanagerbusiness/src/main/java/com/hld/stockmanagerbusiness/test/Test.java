package com.hld.stockmanagerbusiness.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Test {
    public static void main(String [] args){

        Test test=new Test();
        Order o1=test.temp("BTC/USDT",1);//卖1
        Order o2= test.temp("ETH/BTC",1);//卖1
        Order o3=test.temp("ETH/USDT",2);//买1

        Order s1=test.temp("ETH/USDT",1);//卖1
        Order s2= test.temp("ETH/BTC",2);//买1
        Order s3=test.temp("BTC/USDT",2);//买1



        System.out.println("===================================="+o1+"    "+o2+"     "+o3);
        System.out.println("初始:"+o1.price);
        System.out.println("算回来:"+(1/o2.price*o3.price));


        System.out.println("====================================");
        System.out.println("初始:"+s1.price);
        System.out.println("算回来:"+(1*s2.price*s3.price));





//        System.out.println(""+(6374*0.0061030059499999996));
//        double temp=o1.price*o2.price;
//        System.out.println(""+(451.36621716d*469.15));
    }

    private Order temp(String name,int side){
        if(side==1){
            Order sell1=getOrder(name,1,0);//卖1
            return sell1;
        }
        if(side==2){
            Order buy1=getOrder(name,2,0);//卖1
            return buy1;
        }
        return null;
    }



    private Order getOrder(String name,int side,int index){
        String params="limit=10&market="+name+"&offset=0&side="+side+"";


        HttpClient httpclient = HttpClients.createDefault();

        String url="https://api.hotbit.io/api/v1/order.book?"+params;
        HttpGet httpGet=new HttpGet(url);
        try {
            HttpResponse response = httpclient.execute(httpGet);
            HttpEntity he = response.getEntity();
            BufferedReader br=new BufferedReader(new InputStreamReader(he.getContent()));
            JSONObject json= JSON.parseObject(br.readLine());
            if(json==null){
                return null;
            }
            JSONObject json1=json.getJSONObject("result");
            if(json1==null){
                return null;
            }

            JSONArray ja = json1.getJSONArray("orders");
            JSONObject jo = ja.getJSONObject(index);//卖1
            Order order=new Order(jo.getDouble("price"),jo.getDouble("left"));
            return order;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public class Order{
        private double price;
        private double num;

        public Order(double price,double num){
            this.price=price;
            this.num=num;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getNum() {
            return num;
        }

        public void setNum(double num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "price=" + price +
                    ", num=" + num +
                    '}';
        }
    }


}

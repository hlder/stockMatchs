package com.lhyone.jrjapi;

import com.lhyone.jrjapi.Hq;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Think on 2017/1/17.
 */
public class TestMain {
    /**
     * 证券摘要单只股票
     * @throws Exception
     */
    public static void hangqingsingle() throws Exception{
//        sz000933
        URL url = new URL("http://sjhq.itougu.jrj.com.cn/hq/summary/sh/stock/000933");
        URLConnection urlc = url.openConnection();

        Hq.HqPackage hqPackage = Hq.HqPackage.parseFrom(urlc.getInputStream());
        Hq.SecuritySummary securitySummary = hqPackage.getSecuritySummary();

        System.out.println(securitySummary.getSecurityName());

        System.out.println(hqPackage.getRetCode());
    }

    /**
     * 证券摘要批量
     * @throws Exception
     */
    public static void hangqingbatch() throws Exception{
        URL url = new URL("http://sjhq.itougu.jrj.com.cn/hq/summary");
        URLConnection urlc = url.openConnection();
        urlc.setRequestProperty("content-type", "application/ocet-stream");
        // 发送POST请求必须设置如下两行
        urlc.setDoOutput(true);
        urlc.setDoInput(true);

        Hq.SecuritySyncList.Builder securitySyncList = Hq.SecuritySyncList.newBuilder();
        Hq.SecuritySyncItem.Builder securitySyncItem = Hq.SecuritySyncItem.newBuilder();
        securitySyncItem.setMarketType(Hq.MarketType.SH);
        securitySyncItem.setSecurityType(Hq.SecurityType.SECURITY_TYPE_STOCK);
        securitySyncItem.setSecurityCode("600201");

        Hq.SecuritySyncItem.Builder securitySyncItem1 = Hq.SecuritySyncItem.newBuilder();
        securitySyncItem1.setMarketType(Hq.MarketType.SH);
        securitySyncItem1.setSecurityType(Hq.SecurityType.SECURITY_TYPE_STOCK);
        securitySyncItem1.setSecurityCode("600277");

        securitySyncList.addSecuritySyncItem(securitySyncItem);
        securitySyncList.addSecuritySyncItem(securitySyncItem1);
        securitySyncList.build().writeTo(urlc.getOutputStream());

        Hq.HqPackage hqPackage = Hq.HqPackage.parseFrom(urlc.getInputStream());
        Hq.SecuritySummaryList securitySummaryList = hqPackage.getSecuritySummaryList();

        List<Hq.SecuritySummary> list =securitySummaryList.getSummaryList();
        for(Hq.SecuritySummary securitySummary:list){
            System.out.println(securitySummary.getSecurityName());
            System.out.println(securitySummary.getLastPx());
        }
    }

    /**
     * 证券快照
     */
    public static void stocksnapshot()throws Exception{
        URL url = new URL("http://sjhq.itougu.jrj.com.cn/hq/snapshot/sz/stock/300009");
        URLConnection urlc = url.openConnection();
        Hq.HqPackage hqPackage = Hq.HqPackage.parseFrom(urlc.getInputStream());
        Hq.Snapshot snapshot = hqPackage.getSnapshot();
        System.out.println(snapshot);
    }

    /**
     * 证券买卖盘（买五，卖五）
     * @throws Exception
     */
    public static void stocksell() throws Exception{
        URL url = new URL("http://sjhq.itougu.jrj.com.cn/hq/quotations/sz/stock/300009");
        URLConnection urlc = url.openConnection();
        Hq.HqPackage hqPackage = Hq.HqPackage.parseFrom(urlc.getInputStream());
        Hq.BuyingAndSelling buyingAndSelling = hqPackage.getBuyingAndSelling();
        System.out.println(buyingAndSelling);
    }
    public static void main(String[] args)  throws Exception{
        //单次摘要
//        TestMain.hangqingsingle();
        //批次摘要
//        hangqingbatch();
//        //快照
        stocksnapshot();
        System.out.println("============================================================");
//        //买五，卖五
        stocksell();
    }
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }
}

package com.hld.stockserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hld.stockserver.bean.StockBean;
import com.hld.stockserver.bean.StockJrjBean;
import com.hld.stockserver.bean.StockJrjSellBuyBean;
import com.hld.stockserver.mapper.StockMapper;
import com.hld.stockserver.service.RedisService;
import com.hld.stockserver.service.StockService;
import com.lhyone.jrjapi.Hq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    StockMapper stockMapper;

    @Autowired
    RedisService redisService;

    //根据股票名称或代码搜索股票
    @Override
    public List<StockBean> searchStock(String queryStr) {
//        return stockMapper.queryStock(queryStr);
        return stockMapper.queryStockFuzzy(queryStr);
    }


    //根据股票代码查询股票详情信息
    @Override
    public StockJrjBean queryStockInfoByCode(String str){
        String local="sz";
        if(str.contains("sz")||str.contains("SZ")){
            local="sz";
            str=str.replace("sz","").replace("SZ","");
        }else if(str.contains("sh")||str.contains("SH")){
            local="sh";
            str=str.replace("sh","").replace("SH","");
        }else{
            return null;
        }
        String redisKey="stockServer"+local+str;
        long nowTime=Calendar.getInstance().getTimeInMillis();

        String redsStr=redisService.get(redisKey);
        if(redsStr!=null&&!"".equals(redsStr)){
            StockJrjBean itemBean=JSON.toJavaObject(JSON.parseObject(redsStr),StockJrjBean.class);
            if((nowTime-itemBean.getUpdateTime())<2000){//时间小于两秒钟，直接返回
                return itemBean;
            }
        }

        Hq.Snapshot snapshot=stocksnapshot(str,local);//
        Hq.BuyingAndSelling BuyingAndSelling=stocksell(str,local);//查询股票的买5和卖5信息
        if(snapshot==null){//没查到
            return null;
        }
        StockJrjBean bean=new StockJrjBean(""+snapshot.getSecurityCode(),""+snapshot.getDate(),""+snapshot.getTime(),""+snapshot.getSecurityName(),""+snapshot.getSecurityType(),
                ""+snapshot.getMarketType(),""+snapshot.getTradingStatus(),""+snapshot.getPreClosePx(),""+snapshot.getOpenPx(),""+snapshot.getHighPx(),""+snapshot.getLowPx(),
                ""+snapshot.getLastPx(),""+snapshot.getPxChg(),""+snapshot.getPxChgRatio(),""+snapshot.getMarketValue(),""+snapshot.getCirculationMarketValue(),""+snapshot.getPe(),
                ""+snapshot.getInnerVolume(),""+snapshot.getOuterVolume(),""+snapshot.getTurnOver(),""+snapshot.getTradeVolume(),""+snapshot.getTradeValue(),
                ""+snapshot.getPxAmplitude(),""+snapshot.getNetValue(),""+snapshot.getHardenPrice(),""+snapshot.getDropstopPrice(),""+snapshot.getMarketstatus(),""+snapshot.getLlPFShare(),
                ""+snapshot.getNLiangbi(),""+snapshot.getNWeibi(),""+snapshot.getLlTotalShare(),""+snapshot.getNEPS(),""+snapshot.getAccumNet());
        Hq.TradeDetailList sellList=BuyingAndSelling.getSell();
        Hq.TradeDetailList buyList=BuyingAndSelling.getBuy();
        List<StockJrjSellBuyBean> listSell=new ArrayList<>();
        List<StockJrjSellBuyBean> listBuy=new ArrayList<>();
        for(int i=0;i<sellList.getTradeDetailCount();i++){
            Hq.TradeDetail td=sellList.getTradeDetail(i);
            listSell.add(new StockJrjSellBuyBean(td.getTradeType()+"",td.getTradeTime()+"",td.getTradeNum()+"",td.getTradePx()+""));
        }
        for(int i=0;i<buyList.getTradeDetailCount();i++){
            Hq.TradeDetail td=buyList.getTradeDetail(i);
            listBuy.add(new StockJrjSellBuyBean(td.getTradeType()+"",td.getTradeTime()+"",td.getTradeNum()+"",td.getTradePx()+""));
        }
        bean.setSecurityCodeStr(str);
        bean.setSell(listSell);
        bean.setBuy(listBuy);
        bean.setUpdateTime(Calendar.getInstance().getTimeInMillis());

//        System.out.println("重新获取");
        redisService.set(redisKey,JSON.toJSONString(bean));
        return bean;
    }








    /**
     * 证券快照
     */
    public static Hq.Snapshot stocksnapshot(String stockCode,String local){
        Hq.Snapshot snapshot=null;
        try{
            URL url = new URL("http://sjhq.itougu.jrj.com.cn/hq/snapshot/"+local+"/stock/"+stockCode);
            URLConnection urlc = url.openConnection();
            Hq.HqPackage hqPackage = Hq.HqPackage.parseFrom(urlc.getInputStream());
            snapshot = hqPackage.getSnapshot();
        }catch (Exception e){}

        return snapshot;
    }
    /**
     * 证券买卖盘（买五，卖五）
     * @throws Exception
     */
    public static Hq.BuyingAndSelling stocksell(String stockCode,String local){
        Hq.BuyingAndSelling buyingAndSelling=null;
        try{
            URL url = new URL("http://sjhq.itougu.jrj.com.cn/hq/quotations/"+local+"/stock/"+stockCode);
            URLConnection urlc = url.openConnection();
            Hq.HqPackage hqPackage = Hq.HqPackage.parseFrom(urlc.getInputStream());
            buyingAndSelling = hqPackage.getBuyingAndSelling();
        }catch (Exception e){}
        return buyingAndSelling;
    }

}

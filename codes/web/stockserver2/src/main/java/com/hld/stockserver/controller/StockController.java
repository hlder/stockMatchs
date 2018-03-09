package com.hld.stockserver.controller;

import com.hld.stockserver.bean.StockBean;
import com.hld.stockserver.bean.StockJrjBean;
import com.hld.stockserver.service.StockManagerService;
import com.hld.stockserver.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/stockServer")
public class StockController {
    @Autowired
    StockService stockService;
    @Autowired
    StockManagerService stockManagerService;

    @RequestMapping("/searchStock")
    @ResponseBody
    public List<StockBean> searchStock(HttpServletRequest request,String searchStr){
        return stockService.searchStock(searchStr);
    }

    @RequestMapping("/queryStockInfoByCode")
    @ResponseBody
    public StockJrjBean queryStockInfoByCode(HttpServletRequest request, String stockCode){
        return stockService.queryStockInfoByCode(stockCode);
    }

    @RequestMapping("/doUpdateStocks")
    @ResponseBody
    public Object doUpdateStocks(HttpServletRequest request){
        stockManagerService.doUpdateStock();
        return "success";
    }

    @RequestMapping("/doSetTest")
    @ResponseBody
    public Object doSetTest(String keyStr,String valueStr){
        Jedis jedis= new Jedis("r-uf66cc4852afc124.redis.rds.aliyuncs.com",6379);
        jedis.auth("Huoling7650");
//        Jedis jedis= new Jedis("192.168.1.11",6379);
        jedis.set(keyStr,valueStr);
        return "ok";
    }
    @RequestMapping("/doGetTest")
    @ResponseBody
    public Object doGetTest(String keyStr){
        Jedis jedis= new Jedis("r-uf66cc4852afc124.redis.rds.aliyuncs.com",6379);
        jedis.auth("Huoling7650");
        return ""+jedis.get(""+keyStr);
    }

}

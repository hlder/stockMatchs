package com.hld.stockserver.controller;

import com.hld.stockserver.bean.StockBean;
import com.hld.stockserver.bean.StockJrjBean;
import com.hld.stockserver.service.StockManagerService;
import com.hld.stockserver.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}

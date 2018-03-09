package com.hld.stockserver.Scheduled;

import com.hld.stockserver.service.StockManagerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTest {
    private Logger logger = Logger.getLogger(ScheduledTest.class);

    @Autowired
    StockManagerService stockManagerService;

    //每日凌晨4:10分更新列表
    @Scheduled(cron="0 10 04 ? * *")
    public void updateAllStocks() {
//        创业板所有股票
//        http://money.finance.sina.com.cn/d/api/openapi_proxy.php/?__s=[["hq","cyb","",0,1,10000]]
//        中小板
//        http://money.finance.sina.com.cn/d/api/openapi_proxy.php/?__s=[["hq","zxqy","",0,1,10000]]
//        全部A股
//        http://money.finance.sina.com.cn/d/api/openapi_proxy.php?__s=[["hq","hs_a","",0,1,10000]]

        stockManagerService.doUpdateStock();
    }



}

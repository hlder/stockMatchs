package com.hld.stockmanagerbusiness.controller;

import com.hld.stockmanagerbusiness.bean.EntrustStockInfo;
import com.hld.stockmanagerbusiness.bean.EntrustStockInfoHistory;
import com.hld.stockmanagerbusiness.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class AccountController extends BaseController {
    @Autowired
    AccountService accountService;

    //查询我的持仓
    @RequestMapping(value="/queryMyHolderInfos",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryMyHolderInfos(String token,String userId,String accountId){
        Map<String,Object> checkMap=checkToken(token,userId+"");
        if(checkMap!=null){
            return checkMap;
        }
        return accountService.queryHolderInfos(accountId);
    }

    //查询我正在的委托
    @RequestMapping(value="/queryMyEntrust")
    @ResponseBody
    public Map<String,Object> queryMyEntrust(String token,String userId,String accountId){
        Map<String,Object> checkMap=checkToken(token,userId+"");
        if(checkMap!=null){
            return checkMap;
        }
        List<EntrustStockInfo> list=accountService.queryMyEntrust(""+accountId);
        return getSuccessMap(list);
    }

    //查询今日历史委托
    @RequestMapping(value="/queryMyEntrustHistoryToday")
    @ResponseBody
    public Map<String,Object> queryMyEntrustHistoryToday(String token,String userId,String accountId){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String startDate=year+"-"+month+"-"+day+" 00:00:00";
        String endtDate=year+"-"+month+"-"+(day+1)+" 00:00:00";
        return queryMyEntrustHistory(token,userId,accountId,""+startDate,""+endtDate,10000);
    }
    //查询历史委托
    @RequestMapping(value="/queryMyEntrustHistory")
    @ResponseBody
    public Map<String,Object> queryMyEntrustHistory(String token,String userId,String accountId,String startDate,String endDate,int page){
        Map<String,Object> checkMap=checkToken(token,userId+"");
        if(checkMap!=null){
            return checkMap;
        }
        List<Object> list=accountService.queryMyEntrustHistory(accountId,startDate,endDate,page);
        return getSuccessMap(list);
    }

    //撤单
    @RequestMapping(value="/revokeMyEntrust")
    @ResponseBody
    public Map<String,Object> revokeMyEntrust(String token,String userId,String accountId,String entrustId){
        Map<String,Object> checkMap=checkToken(token,userId+"");
        if(checkMap!=null){
            return checkMap;
        }
        boolean flag=accountService.revokeMyEntrust(entrustId);
        if(flag){//true
            return getNoDataMap(ERROR_CODE_SUCCESS);
        }else{
            return getErrorMap(ERROR_CODE_PARAMS,"操作失败");
        }
    }

    //购买股票
    @RequestMapping(value="/entrustBuyStock")
    @ResponseBody
    public Map<String,Object> entrustBuyStock(String token,String userId,String accountId,String stockCode,String stockCodeStr,String stockName,String entrustPrice,int count){
        Map<String,Object> checkMap=checkToken(token,userId+"");
        if(checkMap!=null){
            return checkMap;
        }
        int code=accountService.entrustBuyStock(accountId,stockCode,stockCodeStr,stockName,entrustPrice,count);
        return getNoDataMap(code);
    }

    //卖出股票
    @RequestMapping(value="/entrustSellStock")
    @ResponseBody
    public Map<String,Object> entrustSellStock(String token,String userId,String accountId,String stockCode,String stockCodeStr,String stockName,String entrustPrice,int count){
        Map<String,Object> checkMap=checkToken(token,userId+"");
        if(checkMap!=null){
            return checkMap;
        }
        int code=accountService.entrustSellStock(accountId,stockCode,stockCodeStr,stockName,entrustPrice,count);
        return getNoDataMap(code);
    }


    //模糊搜索
    @RequestMapping(value="/queryStockFuzzy")
    @ResponseBody
    public Map<String,Object> queryStockFuzzy(String token,String userId,String searchStr){
        Map<String,Object> checkMap=checkToken(token,userId+"");
        if(checkMap!=null){
            return checkMap;
        }
        List<Map<String,Object>> listData = accountService.queryStockFuzzy(searchStr);
        return getSuccessMap(listData);
    }

}















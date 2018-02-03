package com.hld.stockmanagerbusiness.controller;

import com.hld.stockmanagerbusiness.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}

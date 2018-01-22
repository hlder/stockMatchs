package com.hld.stockmanagerbusiness.controller;

import com.hld.stockmanagerbusiness.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController extends BaseController {
    @Autowired
    HomeService homeService;

    @RequestMapping(value="/queryHomeInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryHomeInfo(String token,String userId,String matchId,String accountId){
        Map<String,Object> map=checkToken(token,userId+"");
        if(map!=null){
            return map;
        }
        map=homeService.queryHomeInfo(matchId,userId,accountId);
        if(map==null){
            return getErrorMap(ERROR_CODE_OTHER,"查询失败!");
        }
        return getSuccessMap(map);
    }
}

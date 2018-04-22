package com.hld.stockmanagerbusiness.controller;

import com.hld.stockmanagerbusiness.service.UserInfoService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//用户信息
@RestController
public class UserInfoController extends BaseController{
    @Autowired
    UserInfoService userInfoService;


    //查询用户信息的信息
    //参数:leaderAccountId,accountId
    @RequestMapping(value="/queryUserInfo")
    @ResponseBody
    public Map<String,Object> queryUserInfo(HttpServletRequest request){
        return userInfoService.queryUserInfo(request);
    }

    //查询用户的收益曲线
    //参数:leaderAccountId,matchId
    @RequestMapping(value="/queryUserIncomeArr")
    @ResponseBody
    public Map<String,Object> queryUserIncomeArr(HttpServletRequest request){
        return userInfoService.queryUserIncomeArr(request);
    }

}

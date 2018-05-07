package com.hld.stockmanagerbusiness.service;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import java.util.Map;

public interface UserInfoService {
    //查询用户基本信息
    Map<String,Object> queryUserInfo(HttpServletRequest request);
    //查询用户的收入曲线
    Map<String,Object> queryUserIncomeArr(HttpServletRequest request);
    //查询我的leaders
    Map<String,Object> queryMyLeaders(HttpServletRequest request);
    //查询比赛中用户
    Map<String,Object> queryMatchUsers(HttpServletRequest request);
    //关注某人
    Map<String,Object> attenUser(HttpServletRequest request);


}

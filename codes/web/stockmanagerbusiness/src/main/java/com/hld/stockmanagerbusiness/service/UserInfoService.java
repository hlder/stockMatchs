package com.hld.stockmanagerbusiness.service;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import java.util.Map;

public interface UserInfoService {
    Map<String,Object> queryUserInfo(HttpServletRequest request);
    Map<String,Object> queryUserIncomeArr(HttpServletRequest request);

}

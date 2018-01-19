package com.hld.stockmanagerbusiness.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

public class BaseController  {
    public static final int ERROR_CODE_LOGIN_FAILD=100001;//登录失败


    //
    public Map<String,Object> getSuccessMap(Object dataObj){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code",0);
        map.put("data",dataObj);
        return map;
    }
    public Map<String,Object> getErrorMap(long errorCode,String message){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code",errorCode);
        map.put("msg",message);
        return map;
    }
}

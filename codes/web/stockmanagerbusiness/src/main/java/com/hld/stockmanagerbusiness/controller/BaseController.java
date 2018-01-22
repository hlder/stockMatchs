package com.hld.stockmanagerbusiness.controller;

import com.hld.stockmanagerbusiness.service.RedisService;
import com.hld.stockmanagerbusiness.utils.ErrorCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

public class BaseController  {
    public static final int ERROR_CODE_SUCCESS=0;//成功
    public static final int ERROR_CODE_OTHER=999999;//其他错误
    public static final int ERROR_CODE_LOGIN_FAILD=100001;//登录失败
    public static final int ERROR_CODE_NO_DATA=100002;//没数据
    public static final int ERROR_CODE_ALERADY=100003;//已经报名
//    Already
    @Autowired
    RedisService redisService;

    public Map<String,Object> checkToken(String token,String userId){
        String reqtoken=redisService.get("loginTokenUserId"+userId);
        System.out.println("请求用户:userId:"+userId+"    reqtoken:"+reqtoken);
        if(reqtoken==null){//token有问题,重新登录
            return getErrorMap(ErrorCodeUtil.ERROR_CODE_TOKEN,"您的账户验证出错，需要重新登录!");
        }
        if(!reqtoken.equals(token)){//token有问题,重新登录
            return getErrorMap(ErrorCodeUtil.ERROR_CODE_TOKEN,"你的账号已再其他地方登录！");
        }
        return null;
    }


    public Map<String,Object> getNoDataMap(long code){
        String msg="";
        if(code==ERROR_CODE_SUCCESS){//成功
            msg="成功";
        }

        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code",code);
        map.put("msg",msg);
        return map;
    }

    public Map<String,Object> getSuccessMap(Object dataObj){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code",ERROR_CODE_SUCCESS);
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

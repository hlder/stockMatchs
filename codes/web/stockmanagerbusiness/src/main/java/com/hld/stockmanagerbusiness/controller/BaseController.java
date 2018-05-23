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
    public static final int ERROR_CODE_PARAMS=100004;//参数错误
    public static final int ERROR_CODE_AUTH_CODE=1000041;//验证码错误
    public static final int ERROR_NO_MONEY=100005;//钱不够了
    public static final int ERROR_NO_HOLDER=100006;//没持仓
    public static final int ERROR_SEND_SMS_AUTH_CODE=100007;//发送验证码失败
    public static final int ERROR_SEND_SMS_AUTH_CODE_OVER=100008;//超出发送验证码限制
    public static final int ERROR_SEND_SMS_AUTH_CODE_PHONE=100009;//手机号错误

    public static final int ERROR_CODE_NO_THIS_MATCH=100010;//没有此比赛
    public static final int ERROR_CODE_NO_THIS_ACCOUNT=100011;//没有此帐户

    //    Already
    @Autowired
    RedisService redisService;

    public Map<String,Object> checkToken(String token,String userId){
//        String reqtoken=redisService.get("loginTokenUserId"+userId);
//        System.out.println("请求用户:userId:"+userId+"    reqtoken:"+reqtoken);
//        if(reqtoken==null){//token有问题,重新登录
//            return getErrorMap(ErrorCodeUtil.ERROR_CODE_TOKEN,"您的账户验证出错，需要重新登录!");
//        }
//        if(!reqtoken.equals(token)){//token有问题,重新登录
//            return getErrorMap(ErrorCodeUtil.ERROR_CODE_TOKEN,"你的账号已再其他地方登录！");
//        }
        return null;
    }

    private static String getCodeMsg(long code){
        if(code==ERROR_CODE_SUCCESS){
            return "成功";
        }
        if(code==ERROR_CODE_LOGIN_FAILD){
            return "登录失败";
        }
        if(code==ERROR_CODE_NO_DATA){
            return "没有数据";
        }
        if(code==ERROR_CODE_ALERADY){
            return "您已报名了此比赛，不用重复报名!";
        }
        if(code==ERROR_CODE_PARAMS){
            return "参数错误";
        }
        if(code==ERROR_NO_MONEY){
            return "您没有足够的资金！";
        }
        if(code==ERROR_NO_HOLDER){
            return "您的持仓不够!";
        }
        if(code==ERROR_CODE_AUTH_CODE){
            return "验证码错误";
        }
        if(code==ERROR_SEND_SMS_AUTH_CODE){
            return "发送验证码失败";
        }
        if(code==ERROR_SEND_SMS_AUTH_CODE_OVER){
            return "同一个手机号码发送短信验证码,1条/分钟,5条/小时,10条/天!";
        }
        if(code==ERROR_SEND_SMS_AUTH_CODE_PHONE){
            return "手机号错误!";
        }
        if(code==ERROR_CODE_NO_THIS_MATCH){
            return "没有此比赛";
        }
        if(code==ERROR_CODE_NO_THIS_ACCOUNT){
            return "没有此帐户";
        }

        return "发生错误";

    }

    public static Map<String,Object> getNoDataMap(long code){
        String msg=getCodeMsg(code);
        if(code==ERROR_CODE_SUCCESS){//成功
            msg="成功";
        }

        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code",code);
        map.put("msg",msg);
        return map;
    }

    public static Map<String,Object> getSuccessMap(Object dataObj){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code",ERROR_CODE_SUCCESS);
        map.put("data",dataObj);
        return map;
    }
    public static Map<String,Object> getErrorMap(long errorCode){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code",""+errorCode);
        map.put("msg",""+getErrorMap(errorCode,""));
        return map;
    }
    public static Map<String,Object> getErrorMap(long errorCode,String message){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code",""+errorCode);
        map.put("msg",""+message);
        return map;
    }
}

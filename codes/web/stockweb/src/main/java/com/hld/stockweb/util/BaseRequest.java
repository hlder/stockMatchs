package com.hld.stockweb.util;

import java.util.HashMap;
import java.util.Map;

public class BaseRequest {
    public static final int CODE_ERROR_LOGIN=1;


    public static Map<String,Object> getSuccessMap(Object data){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","0");
        map.put("data",data);

        return map;
    }
    public static Map<String,Object> getErrorMap(int code){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code",code+"");
        map.put("msg",""+getErrorMsg(code));
        return map;
    }





    private static String getErrorMsg(int code){
        if(code==CODE_ERROR_LOGIN){
            return "账号或密码错误~";
        }
        return "";
    }
}

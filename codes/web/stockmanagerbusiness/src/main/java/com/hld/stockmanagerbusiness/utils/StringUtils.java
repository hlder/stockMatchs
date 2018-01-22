package com.hld.stockmanagerbusiness.utils;

public class StringUtils {
    public static boolean isEmpty(String str){
        boolean flag=org.springframework.util.StringUtils.isEmpty(str);
        if("undefined".equals(str)){
            return true;
        }
        return flag;
    }
}

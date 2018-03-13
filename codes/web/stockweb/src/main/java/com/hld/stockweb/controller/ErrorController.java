package com.hld.stockweb.controller;

import com.hld.stockweb.bean.UserInfoBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/web")
public class ErrorController {

    @RequestMapping(value = "/loginError")
    @ResponseBody
    public Map<String,Object> loginError(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        map.put("code","1001");
        map.put("msg","请求超时,请重新登录!");
        return map;
    }
}

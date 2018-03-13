package com.hld.stockweb.controller;

import com.hld.stockweb.bean.UserInfoBean;
import com.hld.stockweb.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/web")
public class HomeController {
    @Autowired
    IndexService indexService;


    @RequestMapping(value = "/queryIndexInfo")
    @ResponseBody
    public Map<String,Object> queryIndexInfo(HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        UserInfoBean bean=(UserInfoBean)request.getSession().getAttribute("userInfo");
//        bean.getId()


        return map;
    }

}

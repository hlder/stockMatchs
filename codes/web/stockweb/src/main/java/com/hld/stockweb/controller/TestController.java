package com.hld.stockweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//@Controller
@RestController
@RequestMapping(value="/api")
public class TestController {

    @RequestMapping(value = "/testApi")
    public String testApi(HttpServletRequest request){
        System.out.println("====================================================");
        Object tokenObj=request.getSession().getAttribute("token");
        System.out.println("tokenObj:"+tokenObj);
        request.getSession().setAttribute("token","adasdasdasdasasd");
        return "aaaaaaaaaa";
    }

}

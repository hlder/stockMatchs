package com.hld.stockweb.controller;

import com.hld.stockweb.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/web")
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/login")
    @ResponseBody
    public Map<String,Object> testApi(HttpServletRequest request, String userName, String passWord){
        return loginService.doLogin(request,userName,passWord);
    }





}

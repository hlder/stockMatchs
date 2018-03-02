package com.hld.stockweb.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LoginService {
    Map<String,Object> doLogin(HttpServletRequest request, String userName, String passWord);
}

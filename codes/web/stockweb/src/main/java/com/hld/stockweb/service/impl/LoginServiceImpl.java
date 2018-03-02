package com.hld.stockweb.service.impl;

import com.hld.stockweb.bean.UserInfoBean;
import com.hld.stockweb.mapper.LoginMapper;
import com.hld.stockweb.service.LoginService;
import com.hld.stockweb.util.BaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    LoginMapper loginMapper;

    @Override
    public Map<String,Object> doLogin(HttpServletRequest request, String userName, String passWord) {
        UserInfoBean bean=loginMapper.login(userName,passWord);
        if(bean!=null){//登录成功
            request.getSession().setAttribute("userInfo",bean);
            return BaseRequest.getSuccessMap(bean);
        }else{//登录失败,账号或密码不正确
            return BaseRequest.getErrorMap(BaseRequest.CODE_ERROR_LOGIN);
        }
    }
}

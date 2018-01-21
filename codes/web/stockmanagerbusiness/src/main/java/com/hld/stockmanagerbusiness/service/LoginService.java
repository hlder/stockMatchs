package com.hld.stockmanagerbusiness.service;

import com.hld.stockmanagerbusiness.bean.UserInfo;

public interface LoginService {
    UserInfo doLoginWx(String encryptedData,String iv,String code);
    UserInfo doLogin(String openid, String nickname, String sex, String province, String city, String headimgurl, String unionid, String privilege);
}

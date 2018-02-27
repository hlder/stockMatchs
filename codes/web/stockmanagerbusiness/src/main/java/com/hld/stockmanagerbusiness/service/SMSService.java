package com.hld.stockmanagerbusiness.service;

public interface SMSService {
    //发送验证码
    int sendAuthCode(String matchId,String phoneNum);
}

package com.hld.stockmanagerbusiness.service.impl;

import com.hld.stockmanagerbusiness.bean.AuthCodeInfo;
import com.hld.stockmanagerbusiness.controller.BaseController;
import com.hld.stockmanagerbusiness.mapper.SMSMapper;
import com.hld.stockmanagerbusiness.service.RedisService;
import com.hld.stockmanagerbusiness.service.SMSService;
import com.hld.stockmanagerbusiness.utils.SMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSServiceImpl implements SMSService {
    @Autowired
    SMSMapper smsMapper;

    public static void main(String [] args){
//        int rqCode=SMSUtil.sendSmsCode("17621388251","123123");//发送验证码
        int rqCode=SMSUtil.sendSmsCode("13917313249","123123");//发送验证码

    }


    @Override
    public int sendAuthCode(String mathcId,String phoneNum){
        String code=SMSUtil.getAuthCode(smsMapper);//获取到验证码
        int rqCode=SMSUtil.sendSmsCode(phoneNum,code);//发送验证码
        System.out.println("发送验证码返回:"+rqCode);
        if(rqCode==0){
            smsMapper.addAuthCode(""+code,phoneNum,mathcId);
            smsMapper.addAuthCodeHis(""+code,phoneNum,mathcId);
            return BaseController.ERROR_CODE_SUCCESS;//成功
        }else{//发送失败
            return rqCode;
        }
    }

//    //校验验证码
//    private boolean verifyAuthCode(String phoneNum,String authCode){
//        AuthCodeInfo info=smsMapper.queryAuthCode(phoneNum,authCode);
//        if(info!=null&&!"".equals(info.getAuth_code())){
//            //校验成功
//            //删除
//            smsMapper.deleteAuthCode(""+info.getId());
//            return true;
//        }
//        return false;
//    }
}

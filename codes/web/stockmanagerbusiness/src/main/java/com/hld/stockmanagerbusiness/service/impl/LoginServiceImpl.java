package com.hld.stockmanagerbusiness.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hld.stockmanagerbusiness.bean.UserInfo;
import com.hld.stockmanagerbusiness.mapper.UserMapper;
import com.hld.stockmanagerbusiness.service.LoginService;
import com.hld.stockmanagerbusiness.service.RedisService;
import com.hld.stockmanagerbusiness.utils.Encryptor;
import com.hld.stockmanagerbusiness.utils.HttpUtil;
import com.hld.stockmanagerbusiness.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    RedisService redisService;

    @Autowired
    UserMapper userMapper;

    @Override
    public UserInfo doLogin(String openid, String nickname, String sex, String province, String city, String headimgurl, String unionid, String privilege) {
        unionid=openid;
        if(StringUtils.isEmpty(openid)||StringUtils.isEmpty(unionid)){
            return null;
        }
        //执行登录
        UserInfo userInfo = userMapper.queryUserByUnionId(unionid);
        if(userInfo==null){//表示不存在,需要注册
            userMapper.registerUser(""+nickname,""+headimgurl,""+unionid,""+openid,""+sex,""+province,""+city,""+privilege);
            userInfo = userMapper.queryUserByUnionId(unionid);
        }else{//已经存在，执行登录
            //更新用户信息
            userMapper.updateUserInfo(""+nickname,""+headimgurl,""+unionid,""+openid,""+sex,""+province,""+city,""+privilege);
//            userInfo = userMapper.queryUserByUnionId(unionid);
        }
        //生成token
        String token=MD5Util.getMD5(userInfo.getId()+""+userInfo.getWx_union_id()+"");
        userInfo.setToken(token);

        redisService.set("loginTokenUserId"+userInfo.getId(),token+"");//将token存在redis
        return userInfo;
    }


    //执行微信登录
    @Override
    public UserInfo doLoginWx(String encryptedData,String iv,String code){
        if(StringUtils.isEmpty(encryptedData)||StringUtils.isEmpty(iv)||StringUtils.isEmpty(code)){
            System.out.println("数据为空:encryptedData:"+encryptedData+"    iv:"+iv+"    code:"+code);
            return null;
        }
//        String appId="wx5f000e25686cf609";
//        String appSecret="e66eb11a2b0d7e103d1259dd072041e3";

        String appId="wxd49ef38ac045d40a";
        String appSecret="dbb6b712b451a61421be9330c23a868f";


        String result=HttpUtil.sendPost("https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code");
        JSONObject jo= JSON.parseObject(result);
        String session_key=jo.getString("session_key");

        if(session_key==null||"".equals(session_key)){//失败
            System.out.println("获取session_key失败");
            return null;
        }

        System.out.println("result:"+result);
        System.out.println("session_key:"+session_key);
        String json=Encryptor.decodeEncryptedData(encryptedData,session_key,iv);//解密
//        {"openId":"oumDq0JwDum8QFQtEb9LdxPyKLpE","nickName":"韩良冬","gender":1,"language":"zh_CN","city":"","province":"Shanghai","country":"China",
// "avatarUrl":"https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEK0s9XFg2fxXjicYSiaR3vnwLBAQwGJfToZXmMM9h9LjDsk177kkXAia6XqjvsxarGBqutGMD4U2xkUg/0",
// "unionId":"o04mlwA3NRSw1UCyzPxDq4LC0cA4",
// "watermark":{"timestamp":1515766788,"appid":"wx87aed6f976b67303"}}
        JSONObject jsonObject=JSON.parseObject(json);
        System.out.println(json);

        //执行登录
        return doLogin(jsonObject.getString("openId"),jsonObject.getString("nickName"),jsonObject.getString("gender"),jsonObject.getString("province"),
                jsonObject.getString("city"),jsonObject.getString("avatarUrl"),jsonObject.getString("unionId"),"");
    }
    public static void main(String[] args){
       new LoginServiceImpl().doLoginWx(
               "Hk6OmQJkTDaoGqLQX35Kj8YgE1F/Fa+pXanL/JBVUVHZ0eMEw7frMN0Krg3vnL8Y9vXnUfUZf2T2n2BZxR7XVIYPQA60vpc5gG6OQ3LMl1MB8mqfAyerG8ClpXGZpbECHJk5d7IXC2INft5N3MlT5JF8PvoBaP+upRYkyLMV2dNjNV5KDw5E+UW9XFVZHR6FHFc0GvMuiNIut6L8E2duPZOiSblfC9ZR+lcmZFBWIyvQI63P8S2WE2i8Lpk1uCX1pPYe5pAuB/BJcr/f6sF2skjADf7zhjKzXD7EQ6oueI++6oJ/E9haFpGOA05j8LzF0/B6ee1lTwNqgIghar2reEoHsA4iyKpcol6imNBNwI892UvBoQci519oVhXwOylROq9AeRFiW7MfIONQunhG/sLfrhDHyjam0Hh83ktQmyhTFz2lnllitTpck/mRMfrRlDtLF40BmNggJryIudxmVL1xO2oWWOxwuB49l1tJ/txaRO/aJzM61zsu27rJTINPNdjIZA+dGx6GOn3xFSCKXA==",
               "xv5N3tjgWFGl1vzZ1ITChw==","071rDHAT0ThU7X16MxAT0RKRAT0rDHAN");

    }




}

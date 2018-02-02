package com.hld.stockmanagerbusiness.service.impl;

import com.hld.stockmanagerbusiness.bean.AccountInfo;
import com.hld.stockmanagerbusiness.bean.MatchInfo;
import com.hld.stockmanagerbusiness.controller.BaseController;
import com.hld.stockmanagerbusiness.mapper.AccountMapper;
import com.hld.stockmanagerbusiness.mapper.MathMapper;
import com.hld.stockmanagerbusiness.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService{
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    MathMapper mathMapper;

    @Override
    public int applyMatch(String userId,String matchId, String name, String phoneNum, String authNum, String profession, String stuClass, String stuNum) {
        List<AccountInfo> list=accountMapper.queryAccountByUserId(userId+"",matchId+"");
        if(list==null|list.size()==0){//没有数据可以报名
            MatchInfo matchInfo=queryApplyMatchInfo(matchId);
            if(matchInfo!=null){
                accountMapper.insertAccount( userId, matchId,  name,  phoneNum,  profession,  stuClass,  stuNum,matchInfo.getInit_total_assets(),matchInfo.getInit_total_assets());
            }else{
                return BaseController.ERROR_CODE_PARAMS;
            }
        }else if(list.size()>0){//有数据不能报名
            return BaseController.ERROR_CODE_ALERADY;
        }
        return BaseController.ERROR_CODE_SUCCESS;
    }

    @Override
    public MatchInfo queryApplyMatchInfo(String matchId){
        return mathMapper.queryApplyMatchInfo(matchId);
    }


    @Override
    public int sendAuthSmsCode(String phone){
        System.out.println("发送验证码:"+phone);
        return 0;
    }

}

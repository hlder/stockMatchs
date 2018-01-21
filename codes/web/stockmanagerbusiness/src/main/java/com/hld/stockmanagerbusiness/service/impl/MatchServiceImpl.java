package com.hld.stockmanagerbusiness.service.impl;

import com.hld.stockmanagerbusiness.bean.AccountInfo;
import com.hld.stockmanagerbusiness.mapper.AccountMapper;
import com.hld.stockmanagerbusiness.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService{
    @Autowired
    AccountMapper accountMapper;

    @Override
    public int applyMatch(long userId,long matchId, String name, String phoneNum, String authNum, String profession, String stuClass, String stuNum) {
        List<AccountInfo> list=accountMapper.queryAccountByUserId(userId+"",matchId+"");
        if(list==null|list.size()==0){//没有数据可以报名

        }else if(list.size()>0){//有数据不能报名

        }


        return 0;
    }
}

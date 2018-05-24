package com.hld.stockmanagerbusiness.service;

import com.hld.stockmanagerbusiness.bean.AccountInfo;
import com.hld.stockmanagerbusiness.bean.MatchInfo;

import java.util.List;

public interface MatchService {
    //报名参加比赛
    int applyMatch(String userId,String matchId,String name,String phoneNum,String authNum,String profession,String stuClass,String stuNum);

    //根据matchId查询比赛信息
    MatchInfo queryApplyMatchInfo(String matchId);
    List<AccountInfo> queryAcountByMatchAndUser(String userId, String matchId);
    boolean checkMatch(String userId,String accountId);
}

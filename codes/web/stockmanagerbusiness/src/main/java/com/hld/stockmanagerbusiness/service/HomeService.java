package com.hld.stockmanagerbusiness.service;

import java.util.Map;

public interface HomeService  {
    //查询主页信息
    Map<String,Object> queryHomeInfo(String matchId,String userId,String accountId);

    //查询我的leader
    Map<String,Object> queryMyLeaders(String matchId,String accountId);


    Map<String,Object> queryMyLeaderById(String accountId,String leaderAccountId);

}

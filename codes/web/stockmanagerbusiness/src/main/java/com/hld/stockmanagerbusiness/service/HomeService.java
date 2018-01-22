package com.hld.stockmanagerbusiness.service;

import java.util.Map;

public interface HomeService  {
    //查询主页信息
    Map<String,Object> queryHomeInfo(String matchId,String userId,String accountId);
}

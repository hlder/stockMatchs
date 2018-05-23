package com.hld.stockmanagerbusiness.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface MineService {
    Map<String,Object> queryMinAllInfo(HttpServletRequest request);
    //查询我的信息
    Map<String,Object> queryMineInfo(HttpServletRequest request);
    //查询我现在参与的比赛
    Map<String,Object> queryJoinMatchs(HttpServletRequest request);
    //查询我参加过的比赛
    Map<String,Object> queryHisMatchs(HttpServletRequest request);

    //切换比赛
    Map<String,Object> checkMatch(HttpServletRequest request);
}

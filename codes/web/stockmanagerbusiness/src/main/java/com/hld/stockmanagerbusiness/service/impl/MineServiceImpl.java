package com.hld.stockmanagerbusiness.service.impl;

import com.hld.stockmanagerbusiness.bean.UserInfo;
import com.hld.stockmanagerbusiness.controller.BaseController;
import com.hld.stockmanagerbusiness.mapper.MineMapper;
import com.hld.stockmanagerbusiness.service.MineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MineServiceImpl implements MineService {
    @Autowired
    MineMapper mineMapper;

    @Override
    public Map<String, Object> queryMinAllInfo(HttpServletRequest request) {
        String userId=request.getParameter("userId");//我的用户ID
        UserInfo userInfo = mineMapper.queryMineInfo(userId);
        List<Map<String,Object>> joinMathcs= mineMapper.queryJoinMatchs(userId);
        List<Map<String,Object>> hisMatchs=mineMapper.queryHisMatchs(userId);

        Map<String,Object> map=new HashMap<>();
        map.put("userInfo",userInfo);
        map.put("joinMathcs",joinMathcs);
        map.put("hisMatchs",hisMatchs);
        return BaseController.getSuccessMap(map);
    }

    @Override
    public Map<String, Object> queryMineInfo(HttpServletRequest request) {
        String userId=request.getParameter("userId");//我的用户ID
        UserInfo userInfo = mineMapper.queryMineInfo(userId);
        return BaseController.getSuccessMap(userInfo);
    }

    @Override
    public Map<String, Object> queryJoinMatchs(HttpServletRequest request) {
        String userId=request.getParameter("userId");//我的用户ID
        return BaseController.getSuccessMap(mineMapper.queryJoinMatchs(userId));
    }

    @Override
    public Map<String, Object> queryHisMatchs(HttpServletRequest request) {
        String userId=request.getParameter("userId");//我的用户ID
        return BaseController.getSuccessMap(mineMapper.queryHisMatchs(userId));
    }

    @Override
    public Map<String, Object> checkMatch(HttpServletRequest request) {
        String userId=request.getParameter("userId");//我的用户ID
        String accountId=request.getParameter("accountId");//我的用户ID
//        String matchId=request.getParameter("matchId");//我的用户ID
        mineMapper.checkMyMatch(userId,accountId);
        return BaseController.getSuccessMap(true);
    }


}

package com.hld.stockmanagerbusiness.service.impl;

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
    public Map<String, Object> queryMatchRanking(HttpServletRequest request) {
        String accountId=request.getParameter("accountId");//我的用户ID
        String matchId=request.getParameter("matchId");//我的用户ID
        int page=Integer.parseInt(request.getParameter("page"));//我的用户ID
        int pageSize=Integer.parseInt(request.getParameter("pageSize"));//我的用户ID

        List<Map<String,Object>> ranking=mineMapper.queryMatchRanking(matchId,page*pageSize,pageSize);
        Map<String,Object> myAccount=mineMapper.queryMatchAccount(accountId);
        Map<String,Object> map=new HashMap<>();
        map.put("ranking",ranking);
        map.put("myAccount",myAccount);

        return BaseController.getSuccessMap(map);
    }

    @Override
    public Map<String, Object> queryMinAllInfo(HttpServletRequest request) {
        String userId=request.getParameter("userId");//我的用户ID
        Map<String,Object> userInfo = mineMapper.queryMineInfo(userId);
        List<Map<String,Object>> joinMathcs= mineMapper.queryJoinMatchs(userId);
        List<Map<String,Object>> hisMatchs=mineMapper.queryHisMatchs(userId);

        if(joinMathcs!=null){
            Map<String,Object> temItem=null;
            for(Map<String,Object> item:joinMathcs){
                String account_id= ""+item.get("account_id");
                if(account_id.equals(userInfo.get("def_account_id")+"")){//
                    temItem=item;
                    joinMathcs.remove(item);
                    break;
                }
            }
            if(temItem!=null){
                joinMathcs.add(0,temItem);
            }
        }

        Map<String,Object> map=new HashMap<>();
        map.put("userInfo",userInfo);
        map.put("joinMathcs",joinMathcs);
        map.put("hisMatchs",hisMatchs);
        return BaseController.getSuccessMap(map);
    }

    @Override
    public Map<String, Object> queryMineInfo(HttpServletRequest request) {
        String userId=request.getParameter("userId");//我的用户ID
        Map<String,Object> userInfo = mineMapper.queryMineInfo(userId);
        return BaseController.getSuccessMap(userInfo);
    }

    @Override
    public Map<String, Object> queryJoinMatchs(HttpServletRequest request) {
        String userId=request.getParameter("userId");//我的用户ID
        Map<String,Object> userInfo = mineMapper.queryMineInfo(userId);

        List<Map<String,Object>> joinMathcs2= mineMapper.queryJoinMatchs(userId);
        if(joinMathcs2!=null){
            ;
            Map<String,Object> temItem=null;
            for(Map<String,Object> item:joinMathcs2){
                String account_id= ""+item.get("account_id");
                if(account_id.equals(userInfo.get("def_account_id")+"")){//
                    temItem=item;
                    joinMathcs2.remove(item);
                }
            }
            if(temItem!=null){
                joinMathcs2.add(0,temItem);
            }
        }
        return BaseController.getSuccessMap(joinMathcs2);
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

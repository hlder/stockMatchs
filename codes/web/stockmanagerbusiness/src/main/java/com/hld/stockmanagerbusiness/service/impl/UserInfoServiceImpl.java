package com.hld.stockmanagerbusiness.service.impl;

import com.hld.stockmanagerbusiness.bean.AccountInfo;
import com.hld.stockmanagerbusiness.bean.EntrustStockInfo;
import com.hld.stockmanagerbusiness.bean.EntrustStockInfoHistory;
import com.hld.stockmanagerbusiness.bean.MatchInfo;
import com.hld.stockmanagerbusiness.controller.BaseController;
import com.hld.stockmanagerbusiness.mapper.*;
import com.hld.stockmanagerbusiness.service.UserInfoService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    EntrustMapper entrustMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    MathMapper mathMapper;
    @Autowired
    StockInfoMapper stockInfoMapper;

    //关注用户
    @Override
    public Map<String, Object> attenUser(HttpServletRequest request) {
        String accountId=request.getParameter("accountId");//我的账号ID
        String leaderAccountId=request.getParameter("leaderAccountId");//需要关注的账号ID
        AccountInfo info=accountMapper.queryAccountById(""+accountId);
        if(info.getLeader().contains(",")){//证明有多个leader
            if(info.getLeader().contains(","+leaderAccountId)||info.getLeader().contains(leaderAccountId+",")){//存在,已经是我的leader,不需要再关注
                return BaseController.getErrorMap(BaseController.ERROR_CODE_OTHER,"已关注!");
            }
        }else if(info.getLeader().contains(""+leaderAccountId)){//存在,已经是我的leader,不需要再关注
            return BaseController.getErrorMap(BaseController.ERROR_CODE_OTHER,"已关注!");
        }

        String leaders=info.getLeader()+"";
        if("".equals(leaders)){
            leaders=leaderAccountId+"";
        }else{
            leaders=leaders+","+leaderAccountId;
        }
        accountMapper.attenUser(leaders,accountId+"");
        return BaseController.getSuccessMap("success");
    }

    @Override
    public Map<String, Object> queryMatchUsers(HttpServletRequest request) {
        String matchId=request.getParameter("matchId");
        int page=Integer.parseInt(request.getParameter("page"));
        int pageSize=Integer.parseInt(request.getParameter("pageSize"));
        page=page*pageSize;

        List<Map<String,String>> list=userInfoMapper.queryMatchUsers(matchId,page+"",pageSize+"");
        return BaseController.getSuccessMap(list);
    }


    @Override
    public Map<String,Object> queryUserInfo(HttpServletRequest request) {
        Map<String,Object> mapData=new HashMap<>();

//        String userId=request.getParameter("userId");
        String accountId=request.getParameter("accountId");
        String leaderUserId=request.getParameter("leaderAccountId");

        AccountInfo myAccountInfo=accountMapper.queryAccountById(accountId);
        AccountInfo accountInfo=accountMapper.queryAccountById(leaderUserId);
        if(accountInfo==null){
            return BaseController.getErrorMap(BaseController.ERROR_CODE_OTHER);
        }

        boolean flag=false;//是否是我的leader
        String myLeaders=myAccountInfo.getLeader();
        if(myLeaders!=null){
            String[] mlArr=myLeaders.split(",");

            for(int i=0;i<mlArr.length;i++){
                if((""+leaderUserId).equals(mlArr[i])){//是
                    flag=true;
                }
            }
        }

        mapData.put("account",accountInfo);
        mapData.put("userInfo",userInfoMapper.queryUserInfoByUserId(accountInfo.getUser_id()+""));
        mapData.put("isILeader",flag);

        if(flag){//是我的leader
            List<EntrustStockInfo> listEntrust=entrustMapper.queryMyEntrustById(leaderUserId+"");//所有的委托
            List<EntrustStockInfoHistory> listEntrustHis=entrustMapper.queryMyEntrustHistory20(leaderUserId+"");//所有的委托
            mapData.put("listEntrust",listEntrust);
            mapData.put("listEntrustHis",listEntrustHis);
            mapData.put("holder",stockInfoMapper.queryMyHolderByAccountId(leaderUserId+""));
        }else{//不是我的leader

        }
        return BaseController.getSuccessMap(mapData);

    }

    @Override
    public Map<String, Object> queryUserIncomeArr(HttpServletRequest request) {
        Map<String,Object> mapData=new HashMap<>();

        String leaderAccountId=request.getParameter("leaderAccountId");
        String matchId=request.getParameter("matchId");
        //比赛信息
        MatchInfo matchInfo=mathMapper.queryApplyMatchInfo(matchId);
        if(matchInfo==null){
            return BaseController.getErrorMap(BaseController.ERROR_CODE_OTHER);
        }
        String initTotalAssets =""+matchInfo.getInit_total_assets();

        List<Map<String,String>> listMap=userInfoMapper.queryIncomeArr(leaderAccountId,initTotalAssets+"");
        mapData.put("list",listMap);
        return BaseController.getSuccessMap(mapData);
    }

    @Override
    public Map<String, Object> queryMyLeaders(HttpServletRequest request) {
        String accountId=request.getParameter("accountId");
        String matchId=request.getParameter("matchId");
        //查询我的账户信息
        AccountInfo accountInfo=accountMapper.queryAccountById(accountId);
        //查询比赛的信息，banners，buttons
        MatchInfo matchInfo=mathMapper.queryApplyMatchInfo(matchId);
        if(matchInfo==null){
            return BaseController.getErrorMap(BaseController.ERROR_CODE_NO_THIS_MATCH);
        }
        if(accountInfo==null){
            return BaseController.getErrorMap(BaseController.ERROR_CODE_NO_THIS_ACCOUNT);
        }
        Map<String,Object> mapData= new HashMap<>();

        String myLeaderIds=accountInfo.getLeader();//我关注的leader
        String matchLeaderIds=matchInfo.getLeader();//比赛的leader


        if(myLeaderIds!=null&&!"".equals(myLeaderIds)){
            List<Map<String,String>> listLeader=accountMapper.queryAccountInIds(myLeaderIds);
            mapData.put("myLeaders",listLeader);
        }
        if(matchLeaderIds!=null&&!"".equals(matchLeaderIds)){
            List<Map<String,String>> listLeader=accountMapper.queryAccountInIds(matchLeaderIds);
            mapData.put("otherLeaders",listLeader);
        }
        return BaseController.getSuccessMap(mapData);
    }

}

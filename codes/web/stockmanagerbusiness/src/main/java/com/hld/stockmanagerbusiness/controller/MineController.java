package com.hld.stockmanagerbusiness.controller;

import com.hld.stockmanagerbusiness.service.MineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class MineController extends BaseController{
    @Autowired
    MineService mineService;

    //查询我的页面的所有信息
    @RequestMapping(value="/queryMinAllInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryMinAllInfo(HttpServletRequest request){
        return mineService.queryMinAllInfo(request);
    }

    //查询我的基本信息
    @RequestMapping(value="/queryMineInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryMineInfo(HttpServletRequest request){
        return mineService.queryMineInfo(request);
    }

    //查询我正参加的比赛
    @RequestMapping(value="/queryJoinMatchs",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryJoinMatchs(HttpServletRequest request){
        return mineService.queryJoinMatchs(request);
    }

    //查询我参与过的比赛,最多查询最近20场比赛
    @RequestMapping(value="/queryHisMatchs",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryHisMatchs(HttpServletRequest request){
        return mineService.queryHisMatchs(request);
    }

    //切换比赛
    //accountId
    //matchId
    @RequestMapping(value="/checkMatch",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkMatch(HttpServletRequest request){
        return mineService.checkMatch(request);
    }



}

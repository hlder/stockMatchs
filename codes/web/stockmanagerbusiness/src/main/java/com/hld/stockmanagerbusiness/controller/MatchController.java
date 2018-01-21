package com.hld.stockmanagerbusiness.controller;

import com.hld.stockmanagerbusiness.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MatchController extends BaseController {
    @Autowired
    MatchService matchService;

    @RequestMapping(value="/applyMatch",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> applyMatch(String token,long userId,long matchId,String name,String phoneNum,String authNum,String profession,String stuClass,String stuNum){
        Map<String,Object> checkMap=checkToken(token,userId+"");
        if(checkMap!=null){
            return checkMap;
        }
        return getNoDataMap(matchService.applyMatch(userId,matchId, name, phoneNum, authNum, profession, stuClass, stuNum));
    }
}

package com.hld.stockmanagerbusiness.controller;

import com.hld.stockmanagerbusiness.bean.MatchInfo;
import com.hld.stockmanagerbusiness.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import com.hld.stockmanagerbusiness.utils.StringUtils;
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
    public Map<String,Object> applyMatch(String token,String userId,String matchId,String name,String phoneNum,String authNum,String profession,String stuClass,String stuNum,
    boolean isShowProfession,boolean isShowClass,boolean isShowStuNum){
//    isShowProfession: that.data.isShowProfession,//是否要输入职业
//    isShowClass: that.data.isShowClass,//是否要输入班级
//    isShowStuNum: that.data.isShowStuNum//是否要输入学号
        Map<String,Object> checkMap=checkToken(token,userId+"");
        if(checkMap!=null){
            return checkMap;
        }
        if(StringUtils.isEmpty(matchId)){//没有比赛ID
            return getErrorMap(ERROR_CODE_OTHER,"您没有需要报名的比赛~");
        }
        if(StringUtils.isEmpty(name)){//输入名字
            return getErrorMap(ERROR_CODE_OTHER,"请输入您的名字!");
        }
        if(StringUtils.isEmpty(phoneNum)){//输入手机号
            return getErrorMap(ERROR_CODE_OTHER,"请输入您的手机号!");
        }
        if(StringUtils.isEmpty(authNum)){//输入验证码
            return getErrorMap(ERROR_CODE_OTHER,"请输入验证码!");
        }

        if(isShowProfession&&StringUtils.isEmpty(profession)){//输入职业
            return getErrorMap(ERROR_CODE_OTHER,"请输入您的职业!");
        }
        if(isShowClass&&StringUtils.isEmpty(stuClass)){//输入班级
            return getErrorMap(ERROR_CODE_OTHER,"请输入您所在的班级!");
        }
        if(isShowStuNum&&StringUtils.isEmpty(stuNum)){//输入学号
            return getErrorMap(ERROR_CODE_OTHER,"请输入您的学号!");
        }
        int code=matchService.applyMatch(userId,matchId, name, phoneNum, authNum, profession, stuClass, stuNum);
        if(code==ERROR_CODE_ALERADY){
            return getErrorMap(ERROR_CODE_ALERADY,"您已经报名此比赛，不可以重复报名!");
        }
        return getNoDataMap(code);
    }


    //查询报名的信息
    @RequestMapping(value="/queryApplyMatchInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryApplyMatchInfo(String matchCode){
        MatchInfo matchInfo= matchService.queryApplyMatchInfo(matchCode);
        if(matchInfo!=null){
            return getSuccessMap(matchInfo);
        }
        return getErrorMap(ERROR_CODE_NO_DATA,"没有此比赛");
    }

    @RequestMapping(value="/sendAuthSmsCode",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> sendAuthSmsCode(String token,long userId,String phone){
        Map<String,Object> checkMap=checkToken(token,userId+"");
        if(checkMap!=null){
            return checkMap;
        }
        return getNoDataMap(matchService.sendAuthSmsCode(phone));
    }
}

package com.hld.stockmanagerbusiness.controller;

import com.hld.stockmanagerbusiness.service.H5Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class H5Controller extends BaseController {
    @Autowired
    H5Service h5Service;

    @RequestMapping(value="/queryH5Info")
    @ResponseBody
    public Map<String,Object> queryH5Info(String token, String userId,long id){
        Map<String,Object> checkMap=checkToken(token,userId+"");
        if(checkMap!=null){
            return checkMap;
        }
        String rq=h5Service.queryHtml(id);
        return getSuccessMap(rq);
    }

}

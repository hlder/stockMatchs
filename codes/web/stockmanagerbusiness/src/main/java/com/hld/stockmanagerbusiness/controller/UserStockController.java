package com.hld.stockmanagerbusiness.controller;

import com.hld.stockmanagerbusiness.service.UserStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class UserStockController extends BaseController {
    @Autowired
    UserStockService userStockService;

    public Map<String,Object> queryMyHolderStocks(HttpServletRequest request,String token,String userId){
        Map<String,Object> checkMap=checkToken(token,userId);
        if(checkMap!=null){
            return checkMap;
        }
        return getSuccessMap(userStockService.queryMyHolderStocks(userId));
    }

}

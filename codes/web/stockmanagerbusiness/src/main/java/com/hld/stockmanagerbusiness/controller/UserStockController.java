package com.hld.stockmanagerbusiness.controller;

import com.hld.stockmanagerbusiness.service.RedisService;
import com.hld.stockmanagerbusiness.service.UserStockService;
import com.hld.stockmanagerbusiness.utils.ErrorCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserStockController extends BaseController {
    @Autowired
    RedisService redisService;
    @Autowired
    UserStockService userStockService;

    public Map<String,Object> queryMyHolderStocks(HttpServletRequest request,String token,String userId){
        String rId=redisService.get(token);
        if(rId==null||!rId.equals(userId)){//token有问题,重新登录
            return getErrorMap(ErrorCodeUtil.ERROR_CODE_TOKEN,"您的账户验证出错，需要重新登录!");
        }
        return getSuccessMap(userStockService.queryMyHolderStocks(userId));
    }

}

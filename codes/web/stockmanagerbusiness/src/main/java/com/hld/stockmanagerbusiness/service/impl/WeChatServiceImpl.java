package com.hld.stockmanagerbusiness.service.impl;

import com.hld.stockmanagerbusiness.controller.BaseController;
import com.hld.stockmanagerbusiness.mapper.WeChatMapper;
import com.hld.stockmanagerbusiness.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class WeChatServiceImpl implements WeChatService {
    @Autowired
    WeChatMapper weChatMapper;

    @Override
    public Map<String, Object> uploadWeChatFormId(HttpServletRequest request) {
        String userId=request.getParameter("userId");
        String formId=request.getParameter("formId");

        weChatMapper.insertFormId(userId+"",formId+"");
        return BaseController.getNoDataMap(BaseController.ERROR_CODE_SUCCESS);
    }
}

package com.hld.stockmanagerbusiness.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface WeChatService {
    //上传用户的formId
    Map<String,Object> uploadWeChatFormId(HttpServletRequest request);
}

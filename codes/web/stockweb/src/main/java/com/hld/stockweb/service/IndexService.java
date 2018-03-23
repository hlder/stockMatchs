package com.hld.stockweb.service;

import com.hld.stockweb.bean.UserInfoBean;

import java.util.Map;

public interface IndexService {
    Map<String,Object> queryIndexInfo(UserInfoBean bean);
}

package com.hld.stockmanagerbusiness.service;

import java.util.Map;

public interface AccountService {
    Map<String,Object> queryHolderInfos(String accountId);
}

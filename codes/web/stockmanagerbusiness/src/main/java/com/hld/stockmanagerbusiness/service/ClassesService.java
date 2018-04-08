package com.hld.stockmanagerbusiness.service;

import java.util.Map;

public interface ClassesService {
    Map<String,Object> queryClassListByMold(String mold);

    Map<String,Object> queryClassInfoById(String id);
}

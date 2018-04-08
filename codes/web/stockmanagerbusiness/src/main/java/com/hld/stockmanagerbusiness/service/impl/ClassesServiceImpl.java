package com.hld.stockmanagerbusiness.service.impl;

import com.hld.stockmanagerbusiness.bean.ClassInfo;
import com.hld.stockmanagerbusiness.controller.BaseController;
import com.hld.stockmanagerbusiness.mapper.ClassesMapper;
import com.hld.stockmanagerbusiness.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClassesServiceImpl implements ClassesService {
    @Autowired
    ClassesMapper classesMapper;

    @Override
    public Map<String, Object> queryClassListByMold(String mold) {
        List<ClassInfo> list=classesMapper.queryClassListByMold(mold);
        return BaseController.getSuccessMap(list);
    }

    @Override
    public Map<String, Object> queryClassInfoById(String id) {
        return BaseController.getSuccessMap(classesMapper.queryClassInfoById(id));
    }

}

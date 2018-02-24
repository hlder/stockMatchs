package com.hld.stockmanagerbusiness.service.impl;

import com.hld.stockmanagerbusiness.mapper.H5Mapper;
import com.hld.stockmanagerbusiness.service.H5Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class H5ServiceImpl implements H5Service {

    @Autowired
    H5Mapper h5Mapper;

    @Override
    public String queryHtml(long id) {
        return h5Mapper.queryHtmlById(id);
    }
}

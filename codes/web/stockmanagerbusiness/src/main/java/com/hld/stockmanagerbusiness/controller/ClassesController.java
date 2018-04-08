package com.hld.stockmanagerbusiness.controller;

import com.hld.stockmanagerbusiness.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ClassesController extends BaseController {

    @Autowired
    ClassesService classesService;

    @RequestMapping(value="/queryClassListByMold",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryClassListByMold(String mold){
        return classesService.queryClassListByMold(mold);
    }

    @RequestMapping(value="/queryClassInfoById",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryClassInfoById(String id){
        return classesService.queryClassInfoById(id);
    }
}

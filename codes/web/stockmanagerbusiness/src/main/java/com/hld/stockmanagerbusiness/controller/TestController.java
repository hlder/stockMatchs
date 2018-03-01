package com.hld.stockmanagerbusiness.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
public class TestController {

    @RequestMapping(value="/htmlTest")
    public String htmlTest(){
        return "test";
    }
}

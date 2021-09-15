package com.ssafy.api.controller;


import com.ssafy.api.service.HdfsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class HadoopController {

    @Autowired
    HdfsApiService dao;

    public HadoopController() {
        System.out.println("HadoopController2");
    }
    @RequestMapping("/hadoophdfs/{action}")
    public String put( @PathVariable("action") String action){
        String result = "";
        if(action.equals("put"))
            result = dao.write("하둡 입력을 테스트 합니다...\n"+
                    new Date());
        else if(action.equals("get"))
            result = dao.read("a.txt");
        else if(action.equals("delete"))
            result = dao.delete();
//
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("hadooprw", result);
//        mav.setViewName("hadoopView");
        System.out.println(result);
        return result;
    }
}
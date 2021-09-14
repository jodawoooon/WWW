package com.ssafy.api.controller;


import com.ssafy.api.service.HadoopDAO2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class HadoopController {
    HadoopDAO2 dao;
    public HadoopController() {
        System.out.println("HadoopController2");
    }
    @RequestMapping("/hadoophdfs")
    public ModelAndView put(String action){
        String result = "";
        if(action.equals("put"))
            result = dao.write("하둡 입력을 테스트 합니다...\n"+
                    new Date());
        else if(action.equals("get"))
            result = dao.read("/ja605/message.txt");
        else if(action.equals("delete"))
            result = dao.delete();

        ModelAndView mav = new ModelAndView();
        mav.addObject("hadooprw", result);
        mav.setViewName("hadoopView");
        return mav;
    }
}
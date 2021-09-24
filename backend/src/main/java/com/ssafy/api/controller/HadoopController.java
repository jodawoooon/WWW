package com.ssafy.api.controller;


import com.ssafy.api.service.HdfsApiService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@Controller
public class HadoopController {

    @Autowired
    HdfsApiService dao;

    public HadoopController() {
        System.out.println("HadoopController2");
    }
    @RequestMapping("/hadoophdfs/{action}")
    public String put( @PathVariable("action") String action) throws Exception {
        String result = "";
        if(action.equals("put")){
            System.out.println("put");
            result = dao.write("하둡 입력을 테스트 합니다...\n"+
                    new Date());
        }

        else if(action.equals("get")){
            System.out.println("get");
            result=dao.read("part-r-00000");
        }

//        else if(action.equals("delete"))
//            result = dao.delete();
//
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("hadooprw", result);
//        mav.setViewName("hadoopView");
        System.out.println(result);
        return result;
    }

}
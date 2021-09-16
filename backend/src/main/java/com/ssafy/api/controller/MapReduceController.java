package com.ssafy.api.controller;

import com.ssafy.api.service.MapReduceService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MapReduceController {
    @Resource
    private MapReduceService mapReduceService;


    @PostMapping("/hadoop/test")
    public String wordCount(@RequestParam("jobName")String jobName, @RequestParam("inputPath")String inputPath) throws Exception{

        mapReduceService.wordCount(jobName, inputPath);
        return "Success";
    }
}

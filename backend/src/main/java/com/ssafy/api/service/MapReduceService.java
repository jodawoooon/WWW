package com.ssafy.api.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class MapReduceService {

    @Resource
    private HdfsService hdfsService;
    @Resource
    private ReduceJobsUtils reduceJobsUtils;

    /**
     * 默认reduce输出目录
     */
    private static final String OUTPUT_PATH = "/user/j5a605/output";

    public void wordCount(String jobName, String inputPath) throws Exception {
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(inputPath)) {
            return;
        }
        //输出目录
        String outputPath = "hdfs://cluster2.p.ssafy.io:9000/" + OUTPUT_PATH + "/" + jobName;
        if (hdfsService.existFile(outputPath)) {
            hdfsService.deleteFile(outputPath);
        }
        reduceJobsUtils.getWrodCountJobsConf(jobName, outputPath, inputPath);
    }
}
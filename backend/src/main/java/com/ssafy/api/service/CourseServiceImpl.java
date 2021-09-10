package com.ssafy.api.service;

import com.ssafy.api.response.CourseDataGetRes;
import com.sun.istack.logging.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("courseService")
public class CourseServiceImpl implements CourseService {

    //하둡 마스터 노드 정보
    final private String hdfsIP = "cluster2.p.ssafy.io";
    final private String hdfsPort = "8999";

    // 로그 파일 생성 및 로그 출력을 위한 log4j 프레임워크의 자바 객체
    private Logger log = Logger.getLogger(this.getClass());


    @Override
    public List<CourseDataGetRes> getCourseList() {
        return null;
    }
}

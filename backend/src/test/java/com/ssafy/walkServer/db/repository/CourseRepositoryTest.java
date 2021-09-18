package com.ssafy.walkServer.db.repository;

import com.ssafy.api.request.CourseReq;
import com.ssafy.api.response.user.CourseBody;
import com.ssafy.api.service.CourseService;
import com.ssafy.db.repository.CourseQueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;

@SpringBootTest
public class CourseRepositoryTest {

    @Autowired
    CourseQueryRepository courseQueryRepository;

    // 코스 목록 조회 테스트
    @Transactional
    @Test
    public void readCourseList() {
        CourseReq courseReq = new CourseReq();

        courseReq.setUserId("1909077408");
        courseReq.setPage(0);
        courseReq.setSize(10);
        courseReq.setSort("distance");
        courseReq.setMinTime(0);
        courseReq.setMaxTime(240);
        courseReq.setMinDistnace(0);
        courseReq.setMaxDistance(10);
        courseReq.setDong("삼성동");
        courseReq.setLatitude(37.508690392046);
        courseReq.setLongtitude(127.05618275253);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<CourseBody> results = courseQueryRepository.findCourseList(courseReq, pageRequest);

//        System.out.println(results.getPageable());
//        System.out.println(results.getTotalElements());
//        System.out.println(results.getTotalPages());
//        System.out.println(results.getContent());
//
//        for(CourseBody courseBody: results) {
//            System.out.println(courseBody.getGeoDistance());
//        }

    }
}

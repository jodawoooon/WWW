package com.ssafy.api.service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.ssafy.api.request.CourseReq;
import com.ssafy.api.response.CourseDataGetRes;
import com.ssafy.api.response.user.CourseBody;
import com.ssafy.api.response.user.CourseResponseBody;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.repository.CourseQueryRepository;
import com.ssafy.db.repository.CourseRepository;
import com.ssafy.db.repository.CourseReviewQueryRepository;
import com.sun.istack.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("courseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseQueryRepository courseQueryRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseReviewQueryRepository courseReviewQueryRepository;

    @Override
    public BaseResponseBody getCourseList(CourseReq courseReq) {
        PageRequest pageRequest = PageRequest.of(courseReq.getPage(), courseReq.getSize());
        Page<CourseBody> result = courseQueryRepository.findCourseList(courseReq, pageRequest);

        CourseResponseBody courseResponseBody = new CourseResponseBody();

        try {
            courseResponseBody.setMessage("OK");
            courseResponseBody.setStatusCode(200);

            for (CourseBody courseBody : result) {

                List<Double> scoreL = courseReviewQueryRepository.findAvgScoreByCourseId(courseBody.getCourseId());

                double score;

                if(scoreL==null || scoreL.size()==0 || scoreL.get(0)==null){
                    score=0;
                }else{
                    score=scoreL.get(0);
                }

                courseBody.getScore();
                courseResponseBody.getCourseList().add(courseBody);
            }
            courseResponseBody.setPage(result.getNumber());
            courseResponseBody.setTotalPage(result.getTotalPages());
            courseResponseBody.setHasNextPage(result.hasNext());

            return courseResponseBody;
        }catch (Exception e){
            e.printStackTrace();
            courseResponseBody.setMessage("Not Found");
            courseResponseBody.setStatusCode(404);
            return courseResponseBody;
        }
    }

}

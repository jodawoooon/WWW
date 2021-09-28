package com.ssafy.api.service;

import com.ssafy.api.request.CourseReviewReq;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.CourseReview;
import com.ssafy.db.key.CoursePK;
import com.ssafy.db.repository.CourseRepository;
import com.ssafy.db.repository.CourseReviewRepository;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseReviewRepository courseReviewRepository;

    @Override
    @Transactional
    public BaseResponseBody createReview(CourseReviewReq courseReviewReq) {
        BaseResponseBody baseResponseBody = new BaseResponseBody();

        CourseReview courseReview = CourseReview.builder()
                .course(courseRepository.findByCourseId(courseReviewReq.getCourseId()))
                .user(userRepository.findByUserId(courseReviewReq.getUserId()))
                .score(courseReviewReq.getScore())
                .build();

        baseResponseBody.setMessage("OK");
        baseResponseBody.setStatusCode(201);

        return baseResponseBody;
    }

    @Override
    @Transactional
    public BaseResponseBody updateReview(CourseReviewReq courseReviewReq) {
        BaseResponseBody baseResponseBody = new BaseResponseBody();

        CoursePK coursePK = CoursePK.builder()
                .course(courseReviewReq.getCourseId())
                .user(courseReviewReq.getUserId())
                .build();

        CourseReview courseReview = courseReviewRepository.findById(coursePK).get();
        courseReview.updateScore(courseReviewReq.getScore());

        baseResponseBody.setMessage("OK");
        baseResponseBody.setStatusCode(200);

        return baseResponseBody;
    }

    @Override
    @Transactional
    public BaseResponseBody deleteReview(CourseReviewReq courseReviewReq) {
        BaseResponseBody baseResponseBody = new BaseResponseBody();

        CoursePK coursePK = CoursePK.builder()
                .course(courseReviewReq.getCourseId())
                .user(courseReviewReq.getUserId())
                .build();

        CourseReview courseReview = courseReviewRepository.findById(coursePK).get();
        courseReviewRepository.delete(courseReview);

        baseResponseBody.setMessage("OK");
        baseResponseBody.setStatusCode(200);

        return baseResponseBody;
    }
}

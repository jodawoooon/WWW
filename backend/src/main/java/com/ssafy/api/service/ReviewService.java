package com.ssafy.api.service;

import com.ssafy.api.request.CourseReviewReq;
import com.ssafy.common.model.response.BaseResponseBody;

public interface ReviewService {

    public BaseResponseBody createReview(CourseReviewReq courseReviewReq);

    public BaseResponseBody updateReview(CourseReviewReq courseReviewReq);

    public BaseResponseBody deleteReview(CourseReviewReq courseReviewReq);
}

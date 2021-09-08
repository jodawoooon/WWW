package com.ssafy.api.service;

import com.ssafy.common.model.response.BaseResponseBody;

public interface ReviewService {
    public BaseResponseBody readReviewAverage(int courseId);

    public BaseResponseBody createReview();


    public BaseResponseBody updateReview();

    public BaseResponseBody deleteReview(int courseId);
}

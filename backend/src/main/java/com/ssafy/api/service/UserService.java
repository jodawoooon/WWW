package com.ssafy.api.service;

import com.ssafy.common.model.response.BaseResponseBody;

public interface UserService {
    public BaseResponseBody readUserInfo(String userId);

    public BaseResponseBody readRecentCourse(String userId);

    public BaseResponseBody readWishCourse(String userId);

    public BaseResponseBody readTotalTime(String userId);

    public BaseResponseBody readTime(String userId, String type);
}

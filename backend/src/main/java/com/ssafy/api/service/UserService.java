package com.ssafy.api.service;

import com.ssafy.common.model.response.BaseResponseBody;

public interface UserService {
    public BaseResponseBody readUserInfo(int userId);

    public BaseResponseBody readRecentCourse(int userId);

    public BaseResponseBody readWishCourse(int userId);

    public BaseResponseBody readTotalTime(int userId);

    public BaseResponseBody readTime(int userId, String date, String type);
}

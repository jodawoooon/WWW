package com.ssafy.api.service;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;

import java.util.HashMap;

public interface UserService {
    User getUserId(String userId);

    User createUser(String accessToken, String refreshToken, HashMap<String,Object> userInfo);


    public BaseResponseBody readUserInfo(int userId);

    public BaseResponseBody readRecentCourse(int userId);

    public BaseResponseBody readWishCourse(int userId);

    public BaseResponseBody readTotalTime(int userId);

    public BaseResponseBody readTime(int userId, String date, String type);
}

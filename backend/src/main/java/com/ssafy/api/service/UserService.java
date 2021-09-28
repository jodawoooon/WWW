package com.ssafy.api.service;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;

import java.util.HashMap;

public interface UserService {

    User getUserId(String userId);

    User createUser(HashMap<String,Object> Token, HashMap<String,Object> userInfo);

    public BaseResponseBody readUserInfo(String userId);

    public BaseResponseBody readRecentCourse(String userId);

    public BaseResponseBody readWishCourse(String userId);

    public BaseResponseBody readTotalTime(String userId);

    public BaseResponseBody readTime(String userId, String type);
}

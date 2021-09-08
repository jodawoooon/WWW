package com.ssafy.api.service;

import com.ssafy.common.model.response.BaseResponseBody;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Override
    public BaseResponseBody readUserInfo(int userId) {
        return null;
    }

    @Override
    public BaseResponseBody readRecentCourse(int userId) {
        return null;
    }

    @Override
    public BaseResponseBody readWishCourse(int userId) {
        return null;
    }

    @Override
    public BaseResponseBody readTotalTime(int userId) {
        return null;
    }

    @Override
    public BaseResponseBody readTime(int userId, String date, String type) {
        return null;
    }
}

package com.ssafy.api.service;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserId(String userId) {
        Optional<User> user = userRepository.findUserByUserId(userId);
        if(!user.isPresent()){
            return null;
        }
        return user.get();
    }

    @Override
    public User createUser(String accessToken, String refreshToken, HashMap<String, Object> userInfo) {
        User user = new User();

        String id = (String) userInfo.get("userid");
        String name = (String) userInfo.get("name");

        user.setUserId(id);
        user.setName(name);
        // DB에 user 정보 저장
        userRepository.save(user);

        // Redis에 Token값 저장하는 부분

        return user;
    }

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

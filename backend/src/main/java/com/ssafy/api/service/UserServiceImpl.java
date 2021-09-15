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

    @Autowired
    RedisService redisService;

    @Override
    public User getUserId(String userId) {
        Optional<User> user = userRepository.findUserByUserId(userId);
        if(!user.isPresent()){
            return null;
        }
        return user.get();
    }

    @Override
    public User createUser(HashMap<String,Object> Token, HashMap<String, Object> userInfo) {
        String accessToken = (String) Token.get("accessToken");
        String refreshToken = (String) Token.get("refreshToken");
        Long accessTokenExpire = Long.parseLong(Token.get("accessTokenExpire").toString());
        Long refreshTokenExpire = Long.parseLong(Token.get("refreshTokenExpire").toString());

        User user = new User();

        String id = (String) userInfo.get("userId");
        String name = (String) userInfo.get("name");

        user.setUserId(id);
        user.setName(name);

        // DB에 user 정보 저장
        userRepository.save(user);

        // redis에 refreshToken 저장
        redisService.setDataExpire(refreshToken, id, refreshTokenExpire);

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

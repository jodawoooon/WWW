package com.ssafy.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    //public final static long TOKEN_VALIDATION_SECOND = 1000L * 10;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2;     // 2주

    private final RedisTemplate redisTemplate;

    private final StringRedisTemplate stringRedisTemplate;


    public String getData(String key){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setData(String key, String value){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key,value);
        // values.set(name, age, Duration.ofMinutes(1)); // 1분 뒤 메모리에서 삭제된다.
    }

    public void setDataExpire(String key,String value,long duration){
        System.out.println("Redis start!");
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key,value,expireDuration);
    }

    public void deleteData(String key){
        stringRedisTemplate.delete(key);
    }

}

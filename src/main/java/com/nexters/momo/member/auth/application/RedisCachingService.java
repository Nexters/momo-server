package com.nexters.momo.member.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisCachingService {

    private static final String LOGOUT_KEY = "logout";

    private final RedisTemplate<String, String> redisTemplate;

    public void setValuesWithDuration(String key, String data, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public String getValues(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

    public void addBlackList(String accessToken, Long milliSeconds) {
        redisTemplate.opsForValue().set(accessToken, LOGOUT_KEY, milliSeconds, TimeUnit.MILLISECONDS);
    }
}

package com.nexters.momo.config;

import com.nexters.momo.member.auth.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({JwtProperties.class, RedisProperties.class})
public class PropertiesConfig {
}

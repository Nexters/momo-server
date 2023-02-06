package com.nexters.momo.member.auth.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
public class JwtProperties {

    private final Long accessLength;

    private final Long refreshLength;

    private final String accessKey;

    private final String refreshKey;
}

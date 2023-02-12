package com.nexters.momo.member.auth.handler;

import com.nexters.momo.member.auth.application.RedisCachingService;
import com.nexters.momo.member.auth.jwt.JwtProperties;
import com.nexters.momo.member.auth.jwt.JwtTokenFactory;
import com.nexters.momo.member.auth.utils.AuthorizationExtractor;
import com.nexters.momo.member.auth.utils.AuthorizationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtLogoutHandler implements LogoutHandler {

    private final JwtTokenFactory jwtTokenFactory;

    private final RedisCachingService redisCachingService;

    private final Long accessExpirationMillis;

    public JwtLogoutHandler(JwtProperties jwtProperties, JwtTokenFactory jwtTokenFactory, RedisCachingService redisCachingService) {
        this.accessExpirationMillis = jwtProperties.getAccessLength();
        this.jwtTokenFactory = jwtTokenFactory;
        this.redisCachingService = redisCachingService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String accessToken = convert(request);
        log.info("[AccessToken] : {}", accessToken);

        if (isInvalidAccessToken(accessToken)) {
            return;
        }

        String userIdFromToken = jwtTokenFactory.getUserIdFromToken(accessToken);
        if (StringUtils.hasText(userIdFromToken) && isHasRefreshToken(userIdFromToken)) {
            // delete refresh token
            redisCachingService.deleteValues(userIdFromToken);

            // accessToken add black list
            redisCachingService.addBlackList(accessToken, accessExpirationMillis);

            SecurityContextHolder.clearContext();
        }
    }

    private boolean isHasRefreshToken(String userIdFromToken) {
        return StringUtils.hasText(redisCachingService.getValues(userIdFromToken));
    }

    private boolean isInvalidAccessToken(String accessToken) {
        return !StringUtils.hasText(accessToken) || !jwtTokenFactory.isValidAccessToken(accessToken);
    }

    private String convert(HttpServletRequest request) {
        return AuthorizationExtractor.extract(request, AuthorizationType.BEARER);
    }
}


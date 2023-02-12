package com.nexters.momo.member.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexters.momo.common.response.BaseResponse;
import com.nexters.momo.common.response.ResponseCodeAndMessages;
import com.nexters.momo.member.auth.domain.Member;
import com.nexters.momo.member.auth.jwt.JwtToken;
import com.nexters.momo.member.auth.jwt.JwtTokenFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final JwtTokenFactory tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Member member = (Member) authentication.getPrincipal();

        JwtToken jwtToken = tokenProvider.issue(member.getEmail(), convertAuthorities(member));
        ResponseCookie refreshCookie = tokenProvider.createRefreshCookie(jwtToken.getRefreshToken());

        setAuthenticationSuccessHeader(response, refreshCookie.toString());

        objectMapper.writeValue(response.getWriter(), new BaseResponse<>(ResponseCodeAndMessages.MEMBER_LOGIN_SUCCESS, jwtToken));
    }

    private static List<String> convertAuthorities(Member member) {
        return member.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    private static void setAuthenticationSuccessHeader(HttpServletResponse response, String cookie) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader(HttpHeaders.SET_COOKIE, cookie);
    }
}

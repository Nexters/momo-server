package com.nexters.momo.member.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexters.momo.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.nexters.momo.common.response.ResponseCodeAndMessages.MEMBER_UNAUTHORIZED;

@RequiredArgsConstructor
public class LoginAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        setUnauthorizedHeader(response);
        BaseResponse<Void> baseResponse = new BaseResponse<>(MEMBER_UNAUTHORIZED);
        objectMapper.writeValue(response.getWriter(), baseResponse);
    }

    private static void setUnauthorizedHeader(HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}

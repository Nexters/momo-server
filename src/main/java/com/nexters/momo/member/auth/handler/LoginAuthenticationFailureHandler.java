package com.nexters.momo.member.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexters.momo.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final String ERROR_MESSAGE = "Invalid Username or Password";

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        setAuthenticationFailureHeader(response);
        BaseResponse<Void> baseResponse = new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), ERROR_MESSAGE, null);
        objectMapper.writeValue(response.getWriter(), baseResponse);
    }

    private static void setAuthenticationFailureHeader(HttpServletResponse response) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}

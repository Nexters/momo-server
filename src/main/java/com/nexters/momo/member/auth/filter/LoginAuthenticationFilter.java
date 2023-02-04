package com.nexters.momo.member.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexters.momo.member.auth.filter.dto.MemberLoginDto;
import com.nexters.momo.member.auth.token.LoginAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private ObjectMapper objectMapper;

    public LoginAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/auth/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!isContentTypeJson(request)) {
            throw new IllegalStateException("Authentication is not supported");
        }

        MemberLoginDto memberLoginDto = objectMapper.readValue(request.getReader(), MemberLoginDto.class);
        if (isNoUserInformation(memberLoginDto)) {
            throw new IllegalArgumentException("UserId is empty");
        }

        LoginAuthenticationToken authenticationToken = new LoginAuthenticationToken(memberLoginDto, memberLoginDto.getPassword());

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    private boolean isNoUserInformation(MemberLoginDto memberLoginDto) {
        return ObjectUtils.isEmpty(memberLoginDto.getEmail()) ||
                ObjectUtils.isEmpty(memberLoginDto.getPassword()) ||
                ObjectUtils.isEmpty(memberLoginDto.getUuid());
    }

    private boolean isContentTypeJson(HttpServletRequest request) {
        String header = request.getContentType();
        return (header != null) && header.contains(MediaType.APPLICATION_JSON_VALUE);
    }

}

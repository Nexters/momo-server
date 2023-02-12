package com.nexters.momo.member.auth.filter;


import com.nexters.momo.member.auth.application.MemberContext;
import com.nexters.momo.member.auth.application.MemberDetailsService;
import com.nexters.momo.member.auth.application.RedisCachingService;
import com.nexters.momo.member.auth.domain.Member;
import com.nexters.momo.member.auth.domain.Role;
import com.nexters.momo.member.auth.jwt.JwtTokenFactory;
import com.nexters.momo.member.auth.utils.AuthorizationExtractor;
import com.nexters.momo.member.auth.utils.AuthorizationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String MEMBER_UNAUTHORIZED_MESSAGE = "해당 유저는 인증되지 않았습니다.";
    private static final String MEMBER_ACCESS_TOKEN_EXPIRED_MESSAGE = "해당 유저의 토큰이 만료 되었습니다.";

    private final JwtTokenFactory jwtTokenFactory;

    private final MemberDetailsService memberDetailsService;

    private final RedisCachingService redisCachingService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);

        String accessToken = extractAccessTokenFromHeader(request);
        log.info("[AccessToken] : {}", accessToken);

        if (isAlreadyLogout(accessToken)) {
            throw new AccountExpiredException(MEMBER_UNAUTHORIZED_MESSAGE);
        }

        // 토큰은 없지만 로그인 페이지, 회원 가입 페이지에 접근하기 위해 익명 사용자 토큰을 발급한다.
        if (!StringUtils.hasText(accessToken)) {
            SecurityContextHolder.getContext().setAuthentication(anonymousAuthentication());
            filterChain.doFilter(wrappingRequest, wrappingResponse);
            wrappingResponse.copyBodyToResponse();
            return;
        }

        // TODO : 토큰이 만료된 경우 이를 Client측에 알려서 재발급 받도록 해야 한다
        if (isAccessTokenExpired(accessToken)) {
            throw new CredentialsExpiredException(MEMBER_ACCESS_TOKEN_EXPIRED_MESSAGE);
        }

        SecurityContextHolder.getContext().setAuthentication(createAuthentication(accessToken));
        log.info("[Authentication 등록 완료]");

        filterChain.doFilter(wrappingRequest, wrappingResponse);
        wrappingResponse.copyBodyToResponse();
    }

    private boolean isAccessTokenExpired(String accessToken) {
        return !jwtTokenFactory.isValidAccessToken(accessToken);
    }

    private boolean isAlreadyLogout(String accessToken) {
        return StringUtils.hasText(accessToken) && redisCachingService.getValues(accessToken) != null;
    }

    private String extractAccessTokenFromHeader(HttpServletRequest request) {
        return AuthorizationExtractor.extract(request, AuthorizationType.BEARER);
    }

    private UsernamePasswordAuthenticationToken createAuthentication(String accessToken) {
        String userEmail = jwtTokenFactory.getUserEmailFromToken(accessToken);
        Collection<GrantedAuthority> rolesFromToken = jwtTokenFactory.getRolesFromToken(accessToken);

        MemberContext memberContext = (MemberContext) memberDetailsService.loadUserByUsername(userEmail);
        Member findMember = memberContext.getMember();

        log.info("[User Info] : {}", findMember.getEmail());
        return new UsernamePasswordAuthenticationToken(findMember, "", rolesFromToken);
    }

    protected Authentication anonymousAuthentication() {
        Member anonymousMember = new Member("anonymous@anonymous.com", "", "anonymous", "", Role.ANONYMOUS, null);
        return new AnonymousAuthenticationToken("anonymous", anonymousMember, anonymousMember.getAuthorities());
    }
}

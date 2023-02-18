package com.nexters.momo.member.auth.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexters.momo.common.response.ErrorResponse;
import com.nexters.momo.member.auth.application.MemberContext;
import com.nexters.momo.member.auth.application.MemberDetailsService;
import com.nexters.momo.member.auth.application.RedisCachingService;
import com.nexters.momo.member.auth.domain.Member;
import com.nexters.momo.member.auth.domain.Role;
import com.nexters.momo.member.auth.jwt.JwtToken;
import com.nexters.momo.member.auth.jwt.JwtTokenFactory;
import com.nexters.momo.member.auth.utils.AuthorizationExtractor;
import com.nexters.momo.member.auth.utils.AuthorizationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

import static com.nexters.momo.common.response.ResponseCodeAndMessages.MEMBER_TOKEN_EXPIRED;
import static com.nexters.momo.common.response.ResponseCodeAndMessages.MEMBER_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String MEMBER_REFRESH_TOKEN_EXPIRED_MESSAGE = "해당 유저의 Refresh 토큰이 만료 되었습니다.";
    private static final String COOKIE_NAME = "refreshToken";

    private final JwtTokenFactory jwtTokenFactory;

    private final MemberDetailsService memberDetailsService;

    private final RedisCachingService redisCachingService;

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);

        String accessToken = extractAccessTokenFromHeader(request);
        log.info("[AccessToken] : {}", accessToken);

        if (isAlreadyLogout(accessToken)) {
            log.info("[JwtAuthenticationFilter] : 로그아웃 처리된 Access Token입니다.");

            setResponseHeader(response, HttpStatus.UNAUTHORIZED);
            objectMapper.writeValue(response.getWriter(), ErrorResponse.from(MEMBER_UNAUTHORIZED));
            wrappingResponse.copyBodyToResponse();
            return;
        }

        if (isAnonymousMember(accessToken)) {
            log.info("[JwtAuthenticationFilter] : 익명 사용자의 접근 입니다.");

            SecurityContextHolder.getContext().setAuthentication(anonymousAuthentication());
            filterChain.doFilter(wrappingRequest, wrappingResponse);
            wrappingResponse.copyBodyToResponse();
            return;
        }

        if (isAccessTokenExpired(accessToken)) {
            log.info("[JwtAuthenticationFilter] : Access Token이 만료되었습니다.");

            String refreshToken = getCookieValue(request);
            if(!jwtTokenFactory.isValidRefreshToken(refreshToken)) {
                throw new CredentialsExpiredException(MEMBER_REFRESH_TOKEN_EXPIRED_MESSAGE);
            }

            JwtToken newJwtToken = jwtTokenFactory.reIssue(accessToken, refreshToken);
            log.info("[JwtAuthenticationFilter] : Access Token이 재발급 되었습니다.");

            setResponseHeader(response, HttpStatus.UNAUTHORIZED);
            // FIXME - 요기 논의가 필요할 것 같아요..!
//            objectMapper.writeValue(response.getWriter(), new BaseResponse<>(MEMBER_TOKEN_EXPIRED, newJwtToken));
            objectMapper.writeValue(response.getWriter(), ErrorResponse.from(MEMBER_TOKEN_EXPIRED));
            wrappingResponse.copyBodyToResponse();
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(createAuthentication(accessToken));
        log.info("[Authentication 등록 완료]");

        filterChain.doFilter(wrappingRequest, wrappingResponse);
        wrappingResponse.copyBodyToResponse();
    }

    private static boolean isAnonymousMember(String accessToken) {
        return !StringUtils.hasText(accessToken);
    }

    private static void setResponseHeader(HttpServletResponse response, HttpStatus status) {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(status.value());
        response.setContentType(APPLICATION_JSON_VALUE);
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

    private static String getCookieValue(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                .findFirst()
                .orElseThrow(() -> new CredentialsExpiredException(MEMBER_REFRESH_TOKEN_EXPIRED_MESSAGE))
                .getValue();
    }
}

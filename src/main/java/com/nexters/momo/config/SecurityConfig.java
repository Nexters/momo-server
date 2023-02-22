package com.nexters.momo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexters.momo.member.auth.application.MemberDetailsService;
import com.nexters.momo.member.auth.application.RedisCachingService;
import com.nexters.momo.member.auth.domain.Role;
import com.nexters.momo.member.auth.filter.JwtAuthenticationFilter;
import com.nexters.momo.member.auth.filter.LoginAuthenticationFilter;
import com.nexters.momo.member.auth.handler.JwtLogoutHandler;
import com.nexters.momo.member.auth.handler.JwtLogoutSuccessHandler;
import com.nexters.momo.member.auth.handler.LoginAuthenticationEntryPoint;
import com.nexters.momo.member.auth.handler.LoginAuthenticationFailureHandler;
import com.nexters.momo.member.auth.handler.LoginAuthenticationSuccessHandler;
import com.nexters.momo.member.auth.handler.LoginDeniedHandler;
import com.nexters.momo.member.auth.jwt.JwtProperties;
import com.nexters.momo.member.auth.jwt.JwtTokenFactory;
import com.nexters.momo.member.auth.provider.LoginAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_GET_URI = {
            "/actuator/**", "/swagger-ui/**"
    };

    private static final String[] PUBLIC_POST_URI = {
            "/api/auth/register", "/api/auth/login"
    };

    private final MemberDetailsService memberDetailsService;
    private final ObjectMapper objectMapper;
    private final JwtTokenFactory jwtTokenFactory;
    private final RedisCachingService redisCachingService;
    private final JwtProperties jwtProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(loginAuthenticationProvider());
        auth.userDetailsService(memberDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-ui/**", "/v1/api-docs/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, PUBLIC_GET_URI).permitAll()
                .antMatchers(HttpMethod.POST, PUBLIC_POST_URI).permitAll()
                .antMatchers("/api/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .accessDecisionManager(affirmativeBased());

        http
                .addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), LoginAuthenticationFilter.class);

        http
                .exceptionHandling()
                .authenticationEntryPoint(loginAuthenticationEntryPoint())
                .accessDeniedHandler(loginDeniedHandler());

        http
                .logout()
                .logoutUrl("/api/auth/logout")
                .addLogoutHandler(jwtLogoutHandler())
                .logoutSuccessHandler(jwtLogoutSuccessHandler());
    }

    @Bean
    public JwtLogoutHandler jwtLogoutHandler() {
        return new JwtLogoutHandler(jwtProperties, jwtTokenFactory, redisCachingService);
    }

    @Bean
    public JwtLogoutSuccessHandler jwtLogoutSuccessHandler() {
        return new JwtLogoutSuccessHandler();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenFactory, memberDetailsService, redisCachingService, objectMapper);
    }

    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter(objectMapper);
        loginAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        loginAuthenticationFilter.setAuthenticationSuccessHandler(loginAuthenticationSuccessHandler());
        loginAuthenticationFilter.setAuthenticationFailureHandler(loginAuthenticationFailureHandler());
        return loginAuthenticationFilter;
    }

    @Bean
    public AuthenticationProvider loginAuthenticationProvider() {
        return new LoginAuthenticationProvider(memberDetailsService, passwordEncoder());
    }

    @Bean
    public LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler() {
        return new LoginAuthenticationSuccessHandler(objectMapper, jwtTokenFactory);
    }

    @Bean
    public LoginAuthenticationFailureHandler loginAuthenticationFailureHandler() {
        return new LoginAuthenticationFailureHandler(objectMapper);
    }

    @Bean
    public LoginAuthenticationEntryPoint loginAuthenticationEntryPoint() {
        return new LoginAuthenticationEntryPoint(objectMapper);
    }

    @Bean
    public AccessDeniedHandler loginDeniedHandler() {
        return new LoginDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Role 계층 권한 설정 시작
    private AccessDecisionManager affirmativeBased() {
        return new AffirmativeBased(getAccessDecisionVoters());
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
        accessDecisionVoters.add(roleVoter());
        return accessDecisionVoters;
    }

    @Bean
    public AccessDecisionVoter<? extends Object> roleVoter() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy());

        WebExpressionVoter webExpressVoter = new WebExpressionVoter();
        webExpressVoter.setExpressionHandler(handler);

        return webExpressVoter;
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        return new RoleHierarchyImpl();
    }
    // Role 계층 권한 설정 끝
}

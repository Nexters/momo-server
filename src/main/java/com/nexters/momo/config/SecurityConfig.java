package com.nexters.momo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexters.momo.member.auth.business.MemberDetailsService;
import com.nexters.momo.member.auth.filter.LoginAuthenticationFilter;
import com.nexters.momo.member.auth.handler.LoginAuthenticationEntryPoint;
import com.nexters.momo.member.auth.handler.LoginAuthenticationFailureHandler;
import com.nexters.momo.member.auth.handler.LoginAuthenticationSuccessHandler;
import com.nexters.momo.member.auth.handler.LoginDeniedHandler;
import com.nexters.momo.member.auth.jwt.JwtTokenFactory;
import com.nexters.momo.member.auth.provider.LoginAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberDetailsService memberDetailsService;
    private final ObjectMapper objectMapper;
    private final JwtTokenFactory jwtTokenFactory;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(loginAuthenticationProvider());
        auth.userDetailsService(memberDetailsService).passwordEncoder(passwordEncoder());
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
                .anyRequest().permitAll();

        http
                .addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http
                .exceptionHandling()
                .authenticationEntryPoint(loginAuthenticationEntryPoint())
                .accessDeniedHandler(loginDeniedHandler());
    }

    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter();
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
}

package com.nexters.momo.member.auth.provider;

import com.nexters.momo.member.auth.application.MemberContext;
import com.nexters.momo.member.auth.application.MemberDetailsService;
import com.nexters.momo.member.auth.filter.dto.MemberLoginDto;
import com.nexters.momo.member.auth.token.LoginAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final MemberDetailsService memberDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MemberLoginDto principal = (MemberLoginDto) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        MemberContext memberContext = (MemberContext) memberDetailsService.loadUserByUsername(principal.getEmail());

        if (isNotMatchPassword(password, memberContext)) {
            throw new BadCredentialsException("Invalid Password");
        }

        if (isNotMatchDeviceId(principal, memberContext)) {
            throw new BadCredentialsException("Invalid Device Id");
        }

        return new LoginAuthenticationToken(memberContext.getMember(), null, memberContext.getAuthorities());
    }

    private static boolean isNotMatchDeviceId(MemberLoginDto principal, MemberContext memberContext) {
        return !memberContext.getMember().isSameDeviceId(principal.getUuid());
    }

    private boolean isNotMatchPassword(String password, MemberContext memberContext) {
        return !passwordEncoder.matches(password, memberContext.getPassword());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(LoginAuthenticationToken.class);
    }
}

package com.nexters.momo.member.auth.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class LoginAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private Object credentials;

    /**
     * 사용자 인증 전에 사용된다
     * {@link #isAuthenticated()} will return <code>false</code>.
     * @author jiwoo
     */
    public LoginAuthenticationToken(Object principal, Object credentials) {
        super(null);
        super.setAuthenticated(false);
        this.principal = principal;
        this.credentials = credentials;
    }

    /**
     * 사용자 인증된 이후에 사용된다
     * {@link #isAuthenticated()} will return <code>true</code>.
     * @author jiwoo
     */
    public LoginAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(true);
        this.principal = principal;
        this.credentials = credentials;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}

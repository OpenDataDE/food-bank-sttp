package com.opendatadelaware.feede.config.jwt;

import com.opendatadelaware.feede.model.Token;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by denniskalaygian on 7/5/17.
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private JwtToken jwtToken;
    private Token tokenModel;

    public JwtAuthenticationToken(JwtToken unsafeToken) {
        super(null);
        jwtToken = unsafeToken;
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(JwtToken token, Token tokenModel, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        eraseCredentials();
        jwtToken = token;
        this.tokenModel = tokenModel;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public JwtToken getCredentials() {
        return jwtToken;
    }

    @Override
    public Token getPrincipal() {
        return tokenModel;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        jwtToken = null;
    }

}

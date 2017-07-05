package com.opendatadelaware.feede.config.jwt;

import com.opendatadelaware.feede.config.WebSecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by denniskalaygian on 7/5/17.
 */
public class JwtTokenFilter extends AbstractAuthenticationProcessingFilter {

    public JwtTokenFilter() {
        super("api/user");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {

        String tokenPayload = httpServletRequest.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM);
        JwtToken token = new JwtToken(tokenExtractor.extract(tokenPayload));
        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
    }
}

package edu.fra.uas.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthenticationService {

    private static String AUTH_TOKEN_HEADER_NAME;
    private static String AUTH_TOKEN;

    @Value("${authentication.token.header.name}")
    public void setAuthTokenHeaderName(String authTokenHeaderName) {
        AUTH_TOKEN_HEADER_NAME = authTokenHeaderName;
    }

    @Value("${authentication.token}")
    public void setAuthToken(String authToken) {
        AUTH_TOKEN = authToken;
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if(apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
            throw new BadCredentialsException("Invalid API Key");
        }
        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
    
}

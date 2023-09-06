package edu.fra.uas.security;

import java.util.Enumeration;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthenticationService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthenticationService.class);

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
        logRequest(request);
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if(apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
            throw new BadCredentialsException("Invalid API Key");
        }
        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
    
    private static void logRequest(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        String inReq = "\n--> Incomming request:\n" 
                       + request.getMethod() + " " 
                       + request.getRequestURI() + "\n";
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            inReq = inReq + headerName + " = " + request.getHeader(headerName) + "\n";
        }
        log.debug(inReq);
    }

}

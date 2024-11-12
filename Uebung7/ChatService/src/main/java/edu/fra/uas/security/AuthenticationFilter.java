package edu.fra.uas.security;

import java.io.PrintWriter;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) 
        throws java.io.IOException, jakarta.servlet.ServletException {
            try{
                Authentication authentication = AuthenticationService.getAuthentication((HttpServletRequest) request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = httpResponse.getWriter();
                writer.print(e.getMessage());
                writer.flush();
                writer.close();
            }

            filterChain.doFilter(request, response);
    }
    
}

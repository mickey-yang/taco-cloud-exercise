package com.tacos.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class LoopbackIpRedirectFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServerName().equals("localhost") && request.getHeader("host") != null) {
            UriComponents uri = UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request))
                    .host("127.0.0.1")
                    .build();
            response.sendRedirect(uri.toUriString());
            return;
        }
        filterChain.doFilter(request, response);
    }
}

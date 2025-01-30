package com.app.ecom.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    @Lazy
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.debug("JwtFilter processing request: {}", request.getRequestURI());

        try {
            String token = extractToken(request);
            log.debug("Extracted token: {}", token);

            if (token != null && jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractUsername(token);
                log.debug("Authenticated user email: {}", email);

                System.out.println("claims all XXXXXXXX"+jwtUtil.extractAllClaims(token));

                List<GrantedAuthority> authorities = extractAuthorities(token);
                log.debug("Authenticated user email: {}", email);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        log.debug("Request as being logged : {}",request.toString());
        log.debug(request.getHeader(JWT_CONSTANT.HEADER_STRING));

        String header = request.getHeader(JWT_CONSTANT.HEADER_STRING);
        if (header != null && header.startsWith(JWT_CONSTANT.TOKEN_PREFIX)) {
            return header.substring(JWT_CONSTANT.TOKEN_PREFIX.length());
        }
        return null;
    }

    private List<GrantedAuthority> extractAuthorities(String token) {

    List<String> roles = jwtUtil.extractRoles(token);
    List<GrantedAuthority> authorities = new ArrayList<>();

    for (String role : roles) {
        authorities.add(new SimpleGrantedAuthority(role));
    }

    return authorities;
}
}
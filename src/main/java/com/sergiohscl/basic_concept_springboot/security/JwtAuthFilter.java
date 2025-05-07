package com.sergiohscl.basic_concept_springboot.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sergiohscl.basic_concept_springboot.Service.UsersDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

    private final UsersDetailsService usersDetailsService;

    public JwtAuthFilter(UsersDetailsService usersDetailsService) {
        this.usersDetailsService = usersDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
    
        String header = request.getHeader("Authorization");
        String token = null, username = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7).trim();
            if (!token.isEmpty() && JwtUtil.validateToken(token)) {
                username = JwtUtil.extractUsername(token);
            }            
        }
    
        if (username != null
            && SecurityContextHolder.getContext().getAuthentication() == null) {
    
            UserDetails userDetails = usersDetailsService.loadUserByUsername(username);
            if (JwtUtil.validateToken(token)) {
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    
        // **sempre** deixa a requisição seguir
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/auth/login") ||
            path.startsWith("/swagger") ||
            path.startsWith("/v3/api-docs");
    }

}

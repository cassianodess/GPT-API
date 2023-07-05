package com.cassianodess.gptapi.configurations;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cassianodess.gptapi.repositories.UserRepository;
import com.cassianodess.gptapi.utils.TokenValidator;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository repository;

    @Value("${SECRET}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        
        if(token != null) {
            String subject = TokenValidator.validateToken(token, secret);
            UserDetails userDetails = repository.findByEmail(subject).get();

            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization").replace("Bearer", "").trim();
            
            if(authHeader == null || authHeader.length() == 0) {
                return null;
            }
    
            return authHeader;
        } catch (Exception e) {
            return null;
        }
        

    }
    
}

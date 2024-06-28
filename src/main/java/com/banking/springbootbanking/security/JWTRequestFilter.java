package com.banking.springbootbanking.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.repository.LocalUserRepository;
import com.banking.springbootbanking.service.jwt.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private LocalUserRepository userRepository;

    public JWTRequestFilter(JWTService jwtService, LocalUserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            try {
                String username = jwtService.getUsername(token);
                Optional<LocalUser> opUser = userRepository.findByUsername(username);
                if (opUser.isPresent()) {
                    LocalUser user = opUser.get();
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch(JWTDecodeException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            }
        }
        filterChain.doFilter(request, response);
    }
}

package com.keepu.webAPI.security;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collection;
import java.util.Collections;

@Component
@Slf4j // Agrega esta anotación para logging
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {


        final String authHeader = request.getHeader("Authorization");


        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            username = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Cargar usuario desde la base de datos
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Validar token
                if (jwtService.isTokenValid(jwt, userDetails)) {

                    // Extraer el rol del token JWT
                    String role = jwtService.getRoleFromToken(jwt);

                    // Crear autenticación con el rol del token
                    Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities
                    );

                    // Establecer detalles adicionales
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);;
                } else {
                    log.warn("Token validation failed for user: {}", username);
                }
            } catch (UsernameNotFoundException e) {
                log.error("User not found: {}", username);
            } catch (Exception e) {
                log.error("Error during JWT authentication: {}", e.getMessage(), e);
            }
        } else {
            if (username == null) {
                log.debug("Username is null");
            }
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                log.debug("Authentication already exists in context");
            }
        }

        filterChain.doFilter(request, response);
    }
}
package com.mymart.ecommerce_backend.security;

import com.mymart.ecommerce_backend.entities.UserEntity;
import com.mymart.ecommerce_backend.repos.UserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepo userRepo;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");

        String token = null;
        String email = null;

        if (authHeader != null
                && authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);

            try {

                email =
                        jwtUtil.extractUsername(token);

            } catch (Exception e) {

                logger.error(
                        "JWT Error : " + e.getMessage()
                );
            }
        }

        if (email != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

            UserEntity user =
                    userRepo.findByEmail(email);

            if (user != null
                    && jwtUtil.validateToken(
                    token,
                    user.getEmail()
            )) {

                List<SimpleGrantedAuthority> authorities =

                        user.getRoles()

                                .stream()

                                .map(role -> new SimpleGrantedAuthority(
                                        normalizeRole(role.getName())
                                ))

                                .toList();

                UsernamePasswordAuthenticationToken auth =

                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                authorities
                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request
    ) {

        return request
                .getServletPath()
                .startsWith("/api/user/login");
    }

    private String normalizeRole(String roleName) {
        if (roleName == null || roleName.isBlank()) {
            return "";
        }

        String normalizedRole = roleName.trim().toUpperCase(Locale.ROOT);

        if (normalizedRole.startsWith("ROLE_")) {
            return normalizedRole;
        }

        return "ROLE_" + normalizedRole;
    }
}

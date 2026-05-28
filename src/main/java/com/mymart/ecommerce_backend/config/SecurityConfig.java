package com.mymart.ecommerce_backend.config;

import com.mymart.ecommerce_backend.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // PUBLIC APIs
                        .requestMatchers(
                                "/api/user/register",
                                "/api/user/login",
                                "/api/user/reset-password",
                                "/api/notifications/**",
                                "/api/order/**",
                                "/error"
                        ).permitAll()

                        // PUBLIC ADDRESS APIs
                        .requestMatchers(
                                "/api/user/*/shipping-address",
                                "/api/user/shipping-addresses/*",
                                "/api/user/shipping-address/*"
                        ).permitAll()

                        // PUBLIC PRODUCT CATEGORY APIs
                        .requestMatchers(
                                "/api/admin/product-categories",
                                "/api/admin/product-category/**"
                        ).permitAll()

                        // PUBLIC PRODUCT APIs
                        .requestMatchers(
                                "/api/admin/product",
                                "/api/admin/product/**",
                                "/api/admin/products/category/**"
                        ).permitAll()

                        // PUBLIC PRODUCT READ APIs
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/admin/products/category/**",
                                "/api/admin/product/**",
                                "/api/admin/product/by-name/**",
                                "/api/products/**"
                        ).permitAll()

                        // ADMIN APIs
                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")

                        // USER APIs
                        .requestMatchers("/api/user/**")
                        .hasAnyRole("USER", "ADMIN")

                        // ANY OTHER
                        .anyRequest()
                        .authenticated()
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();
    }
}

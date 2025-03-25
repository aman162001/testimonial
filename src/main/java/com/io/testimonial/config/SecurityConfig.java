package com.io.testimonial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.io.testimonial.filter.JwtFilter;
import com.io.testimonial.service.UserDetailService;
import com.io.testimonial.utils.JWTUtility;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailService userDetailsService;
    private final JWTUtility jwtUtility;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection for all dependencies
    public SecurityConfig(UserDetailService userDetailsService,
            JWTUtility jwtUtility,
            PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtUtility = jwtUtility;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtUtility, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Configure authentication using the injected PasswordEncoder
    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            AuthenticationConfiguration authConfig) throws Exception {

        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

        return authManagerBuilder.build();
    }

    // Move PasswordEncoder to a separate configuration class
    @Configuration
    public static class PasswordEncoderConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
}
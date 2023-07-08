package com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.config;

import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.jwt.JwtAccessDeniedHandler;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.jwt.JwtAuthenticationEntryPoint;
import com.hyundaiautoeverbookmanagement.hyundaiautoeverbookmanagement.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers(HttpMethod.POST, "/api/auth/logout").authenticated()
                                .anyRequest().permitAll())
                .sessionManagement( (s) ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf((csrf) -> csrf.disable())
                .cors((cors) -> cors.disable())
                .exceptionHandling( (e) ->
                        e.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .apply(new JwtSecurityConfig(tokenProvider));
        return http.getOrBuild();
    }
}

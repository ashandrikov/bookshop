package com.shandrikov.bookshop.configuration;

import com.shandrikov.bookshop.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.POST, "/restapi/auth/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/restapi/book").hasAuthority(Role.EDITOR.toString())
                    .requestMatchers("/restapi/admin/**").hasAuthority(Role.ADMINISTRATOR.toString())
                    .requestMatchers("/restapi/cart/**", "/restapi/user/**").hasAuthority(Role.USER.toString())
                    .anyRequest().permitAll()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .httpBasic()
                .and()
                    .csrf().disable()
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

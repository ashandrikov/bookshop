package com.shandrikov.bookshop.configuration;

import com.shandrikov.bookshop.enums.Role;
import com.shandrikov.bookshop.exceptions.FilterChainExceptionHandler;
import com.shandrikov.bookshop.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final FilterChainExceptionHandler filterChainExceptionHandler;

    @Order(HIGHEST_PRECEDENCE)
    @Bean
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
        return http.securityMatcher("/restapi/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/restapi/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/restapi/book").hasAuthority(Role.EDITOR.toString())
                        .requestMatchers("/restapi/admin/**").hasAuthority(Role.ADMINISTRATOR.toString())
                        .requestMatchers("/restapi/cart/**", "/restapi/user/**").hasAuthority(Role.USER.toString())
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf().disable()
                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(filterChainExceptionHandler, LogoutFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                    .requestMatchers("/registration").permitAll()
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/css/*", "/js/*").permitAll()
                    .requestMatchers(HttpMethod.GET,"/api/users").hasAuthority(Role.ADMINISTRATOR.toString())
                    .requestMatchers(HttpMethod.GET,"/api/orders", "/api/cart").hasAuthority(Role.USER.toString())
                    .requestMatchers(HttpMethod.GET,"/api/all-orders").hasAuthority(Role.EDITOR.toString())
                    .requestMatchers("/api/book/**").hasAuthority(Role.EDITOR.toString())
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/api/books", true)
                .and()
                    .rememberMe().tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("R24FfUSP7Y6c1#a")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login")
                .and()
                .build();
    }
}

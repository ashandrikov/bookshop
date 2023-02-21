package com.shandrikov.bookshop.configuration;

import com.shandrikov.bookshop.enums.Role;
import com.shandrikov.bookshop.exceptions.FilterChainExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final FilterChainExceptionHandler filterChainExceptionHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.POST, "/restapi/auth/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/restapi/book").hasAuthority(Role.EDITOR.toString())
                    .requestMatchers("/restapi/admin/**").hasAuthority(Role.ADMINISTRATOR.toString())
                    .requestMatchers("/restapi/cart/**", "/restapi/user/**").hasAuthority(Role.USER.toString())
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login").permitAll()
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
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(filterChainExceptionHandler, LogoutFilter.class)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

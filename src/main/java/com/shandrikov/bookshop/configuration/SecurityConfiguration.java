package com.shandrikov.bookshop.configuration;

import com.shandrikov.bookshop.enums.Role;
import com.shandrikov.bookshop.services.implementations.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final UserServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.POST, "/restapi/auth/signup").permitAll()
                    .requestMatchers(HttpMethod.POST, "/restapi/book").hasAuthority(Role.EDITOR.toString())
                    .requestMatchers(HttpMethod.GET, "/restapi/admin/users").hasAuthority(Role.ADMINISTRATOR.toString())
                        .anyRequest().authenticated()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                    .authenticationProvider(authenticationProvider())
                    .httpBasic()
                .and()
                    .csrf().disable();
        return http.build();
    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(){
//        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(getEncoder());
//        return authenticationProvider;
//    }
//
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}

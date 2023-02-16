package com.shandrikov.bookshop.configuration;

import com.shandrikov.bookshop.enums.Role;
import com.shandrikov.bookshop.repositories.UserRepository;
import com.shandrikov.bookshop.services.implementations.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfiguration {
    private final UserServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final UserRepository userRepository;

    public SecurityConfiguration(UserServiceImpl userDetailsService, JwtAuthenticationFilter jwtAuthFilter, @Lazy AuthenticationProvider authenticationProvider,
                                 UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        this.userRepository = userRepository;
    }

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

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> userRepository.findByLoginIgnoreCase(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(getEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder(12);
    }

//    @Bean
//    public UserService getUserService() {
//        return new UserServiceImpl();
//    }
}

package com.shandrikov.bookshop.services.implementations;

import com.shandrikov.bookshop.DTOs.AuthenticationRequest;
import com.shandrikov.bookshop.DTOs.AuthenticationResponse;
import com.shandrikov.bookshop.configuration.JwtService;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.exceptions.UserExistException;
import com.shandrikov.bookshop.repositories.UserRepository;
import com.shandrikov.bookshop.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.shandrikov.bookshop.utils.StringPool.AUTH_INVALID;
import static com.shandrikov.bookshop.utils.StringPool.USER_AUTHENTICATED;
import static com.shandrikov.bookshop.utils.StringPool.USER_CREATED;
import static com.shandrikov.bookshop.utils.StringPool.USER_EXISTS;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    @Override
    public AuthenticationResponse register(AuthenticationRequest request) {
        if (userRepository.findByLoginIgnoreCase(request.login()).isPresent()){
            log.error(USER_EXISTS);
            throw new UserExistException();
        }
        User user = new User(request);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        log.info(String.format(USER_CREATED, user.getLogin()));
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.login(),
                            request.password()
                    )
            );
        } catch (AuthenticationException e) {
            log.error(AUTH_INVALID);
            throw new BadCredentialsException(AUTH_INVALID);
        }
        var user = userRepository.findByLoginIgnoreCase(request.login())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        log.info(String.format(USER_AUTHENTICATED, user.getLogin()));
        return new AuthenticationResponse(jwtToken);
    }
}
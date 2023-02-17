package com.shandrikov.bookshop.services.implementations;

import com.shandrikov.bookshop.DTOs.AuthenticationRequest;
import com.shandrikov.bookshop.DTOs.AuthenticationResponse;
import com.shandrikov.bookshop.configuration.JwtService;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.repositories.UserRepository;
import com.shandrikov.bookshop.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.shandrikov.bookshop.utils.StringPool.USER_CREATED;
import static com.shandrikov.bookshop.utils.StringPool.USER_EXISTS;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    @Override
    public AuthenticationResponse register(AuthenticationRequest request) {
        if (userRepository.findByLoginIgnoreCase(request.login()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_EXISTS);
        User user = new User(request);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        LOGGER.info(String.format(USER_CREATED, user.getLogin()));
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.login(),
                        request.password()
                )
        );
        var user = userRepository.findByLoginIgnoreCase(request.login())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}

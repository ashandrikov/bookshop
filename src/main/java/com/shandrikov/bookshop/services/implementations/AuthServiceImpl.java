package com.shandrikov.bookshop.services.implementations;

import com.shandrikov.bookshop.DTOs.AuthenticationRequest;
import com.shandrikov.bookshop.DTOs.AuthenticationResponse;
import com.shandrikov.bookshop.jwt.JwtService;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public User register(AuthenticationRequest request) {
        return checkAndSaveUser(request);
    }

    @Override
    public AuthenticationResponse registerWithJWT(AuthenticationRequest request) {
        User user = checkAndSaveUser(request);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
    private User checkAndSaveUser(AuthenticationRequest request) {
        if (userRepository.findByLoginIgnoreCase(request.login()).isPresent()){
            log.error(String.format(USER_EXISTS, request.login()));
            throw new UserExistException(request.login());
        }
        User user = new User(request);
        user.setPassword(encoder.encode(user.getPassword()));
        log.info(String.format(USER_CREATED, user.getLogin()));
        return userRepository.save(user);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    request.login(),
                    request.password()
            );
            authManager.authenticate(authentication);
        } catch (AuthenticationException e) {
            log.error(AUTH_INVALID);
            throw new BadCredentialsException(AUTH_INVALID);
        }
        User user = userRepository.findByLoginIgnoreCase(request.login())
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + request.login()));
        String jwtToken = jwtService.generateToken(user);
        log.info(String.format(USER_AUTHENTICATED, user.getLogin()));
        return new AuthenticationResponse(jwtToken);
    }
}

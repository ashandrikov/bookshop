package com.shandrikov.bookshop.services;


import com.shandrikov.bookshop.DTOs.AuthenticationRequest;
import com.shandrikov.bookshop.DTOs.AuthenticationResponse;
import com.shandrikov.bookshop.domains.User;

public interface AuthService {
    User register(AuthenticationRequest request);
    AuthenticationResponse registerWithJWT(AuthenticationRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}

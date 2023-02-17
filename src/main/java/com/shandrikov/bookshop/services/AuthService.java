package com.shandrikov.bookshop.services;


import com.shandrikov.bookshop.DTOs.AuthenticationRequest;
import com.shandrikov.bookshop.DTOs.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse register(AuthenticationRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}

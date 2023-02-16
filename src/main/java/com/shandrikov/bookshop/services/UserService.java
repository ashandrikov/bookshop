package com.shandrikov.bookshop.services;


import com.shandrikov.bookshop.DTOs.AuthenticationRequest;
import com.shandrikov.bookshop.DTOs.NewPasswordDTO;
import com.shandrikov.bookshop.controllers.AuthenticationResponse;
import com.shandrikov.bookshop.domains.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    AuthenticationResponse register(AuthenticationRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    void deleteUser(String email);
    void updatePassword(User user, NewPasswordDTO password);
    User changeUserRole(String login);
    User lockOrUnlockUser(String login);
}

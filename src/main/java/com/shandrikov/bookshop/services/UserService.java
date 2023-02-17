package com.shandrikov.bookshop.services;


import com.shandrikov.bookshop.DTOs.NewPasswordDTO;
import com.shandrikov.bookshop.domains.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    void deleteUser(String email);
    void updatePassword(User user, NewPasswordDTO password);
    User changeUserRole(String login);
    User lockOrUnlockUser(String login);
}

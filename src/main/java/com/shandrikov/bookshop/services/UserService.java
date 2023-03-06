package com.shandrikov.bookshop.services;


import com.shandrikov.bookshop.DTOs.NewPasswordDTO;
import com.shandrikov.bookshop.DTOs.UserDTO;
import com.shandrikov.bookshop.domains.User;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();
    boolean checkIfUsernameExists(String username);
    void deleteUser(String email);
    void updatePassword(User user, NewPasswordDTO password);
    User changeUserRole(String login);
    User lockOrUnlockUser(String login);
}

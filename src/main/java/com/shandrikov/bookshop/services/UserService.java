package com.shandrikov.bookshop.services;


import com.shandrikov.bookshop.domains.AuthenticationRequest;
import com.shandrikov.bookshop.domains.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User saveUser(AuthenticationRequest request);
    void deleteUser(String email);
    //    Map<String, String> lockOrUnlockUser(LockUserDTO userDTO);
//    User changeUserRole(UserChangeRoleDTO userDTO);
//    void updatePassword(User user, PasswordDTO passwordDTO);
}

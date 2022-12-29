package com.shandrikov.bookshop.controllers;

import com.shandrikov.bookshop.domains.AuthenticationRequest;
import com.shandrikov.bookshop.domains.NewPassword;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.services.implementations.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.shandrikov.bookshop.utils.StringPool.PASSWORD_UPDATED;

@RestController
@RequestMapping("/restapi")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/auth/signup")
    public User signup(@Valid @RequestBody AuthenticationRequest request){
        return userService.saveUser(request);
    }

    @PostMapping("/auth/changepass")
    public Map<String, String> changePassword(@Valid @RequestBody NewPassword newPassword, @AuthenticationPrincipal User user){
//        userService.updatePassword(user, passwordDTO);
        return Map.of("email", user.getUsername(), "status", PASSWORD_UPDATED);
    }

    @GetMapping("/admin/users")
    public List<User> seeListUsers(){
        return userService.findAll();
    }

    @DeleteMapping("/admin/user/{email}")
    public Map<String, String> deleteUser(@PathVariable("email") String email){
        userService.deleteUser(email);
        return Map.of("user", email, "status", "Deleted successfully!");
    }

//    @PutMapping("/admin/user/role")
//    public User changeUserRoles(@RequestBody UserChangeRoleDTO userDTO){
//        return userService.changeUserRole(userDTO);
//    }

//    @PutMapping("/admin/user/access")
//    public Map<String, String> lockUnlockUser(@RequestBody LockUserDTO userDTO){
//        return userService.lockOrUnlockUser(userDTO);
//    }
}

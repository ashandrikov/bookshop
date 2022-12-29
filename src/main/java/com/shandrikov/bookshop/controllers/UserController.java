package com.shandrikov.bookshop.controllers;

import com.shandrikov.bookshop.DTOs.AuthenticationRequest;
import com.shandrikov.bookshop.DTOs.NewPasswordDTO;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.services.implementations.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/admin/users")
    public List<User> seeListUsers(){
        return userService.findAll();
    }

    @PostMapping("/auth/changepass")
    public Map<String, String> changePassword(@Valid @RequestBody NewPasswordDTO newPasswordDTO, @AuthenticationPrincipal User user){
        userService.updatePassword(user, newPasswordDTO);
        return Map.of("login", user.getUsername(), "status", PASSWORD_UPDATED);
    }

    @DeleteMapping("/admin/user/{login}")
    public Map<String, String> deleteUser(@PathVariable("login") String login){
        userService.deleteUser(login);
        return Map.of("login", login, "status", "Deleted successfully!");
    }

    @PutMapping("/admin/togglerole/{login}")
    public User toggleUserRole(@PathVariable("login") String login){
        return userService.changeUserRole(login);
    }

    @PutMapping("/admin/toggleaccess/{login}")
    public User lockUnlockUser(@PathVariable("login") String login){
        return userService.lockOrUnlockUser(login);
    }
}

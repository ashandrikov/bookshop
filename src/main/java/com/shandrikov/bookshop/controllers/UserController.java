package com.shandrikov.bookshop.controllers;

import com.shandrikov.bookshop.exceptions.UserNotFoundException;
import com.shandrikov.bookshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.shandrikov.bookshop.utils.StringPool.ERROR;
import static com.shandrikov.bookshop.utils.StringPool.MESSAGE;
import static com.shandrikov.bookshop.utils.StringPool.USER_DELETED;
import static com.shandrikov.bookshop.utils.StringPool.USER_ROLE_CHANGED;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @PostMapping("/user/togglerole/{login}")
    public String toggleUserRole(@PathVariable("login") String login,
                                 RedirectAttributes redirectAttributes) {
        try {
            userService.changeUserRole(login);
            redirectAttributes.addFlashAttribute(MESSAGE, USER_ROLE_CHANGED);
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute(ERROR, ex.getMessage());
        }
        return "redirect:/api/users";
    }

    @GetMapping("/user/delete/{login}")
    public String deleteUser(@PathVariable(name = "login") String login,
                             RedirectAttributes redirectAttributes){
        try {
            userService.deleteOne(login);
            redirectAttributes.addFlashAttribute(MESSAGE, USER_DELETED);
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute(ERROR, ex.getMessage());
        }
        return "redirect:/api/users";
    }
}

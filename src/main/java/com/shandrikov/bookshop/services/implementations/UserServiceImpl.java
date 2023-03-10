package com.shandrikov.bookshop.services.implementations;

import com.shandrikov.bookshop.DTOs.NewPasswordDTO;
import com.shandrikov.bookshop.DTOs.UserDTO;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.exceptions.UserNotFoundException;
import com.shandrikov.bookshop.repositories.OrderRepository;
import com.shandrikov.bookshop.repositories.UserRepository;
import com.shandrikov.bookshop.services.UserService;
import com.shandrikov.bookshop.utils.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.shandrikov.bookshop.enums.Role.ADMINISTRATOR;
import static com.shandrikov.bookshop.enums.Role.EDITOR;
import static com.shandrikov.bookshop.enums.Role.USER;
import static com.shandrikov.bookshop.utils.StringPool.CANNOT_TOGGLE_ADMINISTRATOR;
import static com.shandrikov.bookshop.utils.StringPool.NO_USER_LOGIN;
import static com.shandrikov.bookshop.utils.StringPool.PASSWORDS_EQUAL;
import static com.shandrikov.bookshop.utils.StringPool.PASSWORD_UPDATED_USER;
import static com.shandrikov.bookshop.utils.StringPool.USER_NOT_FOUND;

@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder encoder;

//         The purpose of this Lazy initialization of PasswordEncoder Bean is to avoid Bean Loop problem
    @Autowired
    public UserServiceImpl(@Lazy PasswordEncoder encoder, UserRepository userRepository, OrderRepository orderRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLoginIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    }

    @Override
    public boolean checkIfUsernameExists(String username) {
        return userRepository.findByLoginIgnoreCase(username).isPresent();
    }

    @Override
    public void updatePassword(User user, NewPasswordDTO passwordDTO) {
        String newPassword = passwordDTO.getUpdatedPassword();
        if (encoder.matches(newPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PASSWORDS_EQUAL);
        } else {
            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);
        }
        log.info(String.format(PASSWORD_UPDATED_USER, user.getUsername()));
    }

    @Override
    public List<UserDTO> findAll() {
        return ObjectMapperUtils.mapAll(userRepository.findAll(), UserDTO.class);
    }

    @Transactional
    @Override
    public void deleteOne(String login) {
        User userByEmail = userRepository.findByLoginIgnoreCase(login).orElseThrow(() -> new UserNotFoundException(NO_USER_LOGIN + login));
        if (userByEmail.getAuthorities().contains(ADMINISTRATOR)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CANNOT_TOGGLE_ADMINISTRATOR);
        }
        log.info(String.format("User '%s' was deleted", login));
        orderRepository.deleteByUser(userByEmail);
        userRepository.delete(userByEmail);
    }

    @Override
    public User changeUserRole(String login) {
        User userByLogin = userRepository.findByLoginIgnoreCase(login).orElseThrow(() -> new UserNotFoundException(NO_USER_LOGIN + login));
        switch (userByLogin.getRole()) {
            case USER -> userByLogin.setRole(EDITOR);
            case EDITOR -> userByLogin.setRole(USER);
            case ADMINISTRATOR ->
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CANNOT_TOGGLE_ADMINISTRATOR);
        }
        log.info(String.format("Role for account '%s' was changed", login));
        return userRepository.save(userByLogin);
    }

    @Override
    public User lockOrUnlockUser(String login) {
        User userByLogin = userRepository.findByLoginIgnoreCase(login)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));
        if (userByLogin.getRole() == ADMINISTRATOR) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CANNOT_TOGGLE_ADMINISTRATOR);
        }

        boolean locked = userByLogin.isAccountNonLocked();
        userByLogin.setAccountNonLocked(!locked);

        log.info(String.format("Account '%s' was LOCKED / UNLOCKED", login));
        return userRepository.save(userByLogin);
    }
}

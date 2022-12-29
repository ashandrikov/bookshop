package com.shandrikov.bookshop.services.implementations;

import com.shandrikov.bookshop.domains.AuthenticationRequest;
import com.shandrikov.bookshop.domains.User;
import com.shandrikov.bookshop.enums.Role;
import com.shandrikov.bookshop.repositories.UserRepository;
import com.shandrikov.bookshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.shandrikov.bookshop.enums.Role.ADMINISTRATOR;
import static com.shandrikov.bookshop.utils.StringPool.CANNOT_REMOVE_ADMINISTRATOR;
import static com.shandrikov.bookshop.utils.StringPool.USER_CREATED;
import static com.shandrikov.bookshop.utils.StringPool.USER_EXISTS;
import static com.shandrikov.bookshop.utils.StringPool.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

//    private PasswordEncoder encoder;

    // The purpose of this Lazy initialization of PasswordEncoder Bean is to avoid Bean Loop problem
//    @Autowired
//    public UserServiceImpl(@Lazy PasswordEncoder encoder) {
//        this.encoder = encoder;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLoginIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    }

    @Override
    public User saveUser(AuthenticationRequest request) {
        User user = new User(request);
//        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.EDITOR);
//        user.setAccountNonLocked(true);
        try {
            userRepository.save(user);
            LOGGER.info(String.format(USER_CREATED, user.getLogin()));
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_EXISTS);
        }
        return user;
    }

//    @Override
//    public void updatePassword(User user, @Valid PasswordDTO passwordDTO) {
//        User userByEmail = userRepository.findByLoginIgnoreCase(user.getUsername()).get();
//        String newPassword = passwordDTO.getNewPassword();
////        if (encoder.matches(newPassword, userByEmail.getPassword())) {
////            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PASSWORDS_EQUAL);
////        } else {
////            userByEmail.setPassword(encoder.encode(newPassword));
//            userRepository.save(userByEmail);
//            LOGGER.info(String.format(PASSWORD_UPDATED_USER, user.getUsername()));
////        }
//    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(String email) {
        Optional<User> userByEmail = userRepository.findByLoginIgnoreCase(email);
        if (userByEmail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
        } else if (userByEmail.get().getAuthorities().contains(ADMINISTRATOR)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CANNOT_REMOVE_ADMINISTRATOR);
        }

        UserDetails auth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LOGGER.info(String.format("User %s was deleted", email));
        userRepository.delete(userByEmail.get());
    }

//    @Override
//    public UserOutDTO changeUserRole(UserChangeRoleDTO userDTO) {
//        User userByEmail = userRepository.findByLogin(userDTO.getEmail())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND));
//        checkRequestBeforeChanging(userDTO, userByEmail);
//
//        String object;
//        UserDetails auth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        switch (OperationRoles.valueOf(userDTO.getOperation())){
//            case GRANT:
//                userByEmail.grantRole(Role.valueOf(userDTO.getRole()));
//                object = String.format("Grant role %s to %s", userDTO.getRole(), userDTO.getEmail());
//                securityEventsService.registerEvent(GRANT_ROLE, auth.getUsername(), object, "/api/admin/user/role");
//                LOGGER.info(String.format("Role %s was GRANTED for user %s", userDTO.getRole(), userDTO.getEmail()));
//                break;
//            case REVOKE:
//                userByEmail.removeRole(Role.valueOf(userDTO.getRole()));
//                object = String.format("Revoke role %s from %s", userDTO.getRole(), userDTO.getEmail());
//                securityEventsService.registerEvent(REMOVE_ROLE, auth.getUsername(), object, "/api/admin/user/role");
//                LOGGER.info(String.format("Role %s was REVOKED from user %s", userDTO.getRole(), userDTO.getEmail()));
//                break;
//            default: return null;
//        }
//        return UserMapper.convertEntityToDTO(userRepository.save(userByEmail));
//    }

//    private void checkRequestBeforeChanging(UserChangeRoleDTO userDTO, User userByEmail) {
//        if (!getAllRolesStrings().contains(userDTO.getRole())) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ROLE_NOT_FOUND);
//        } else if (userDTO.getOperation().equals(OperationRoles.REVOKE.toString()) && !userByEmail.getAuthorities().contains(Role.valueOf(userDTO.getRole()))) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, USER_DOES_NOT_HAVE_ROLE);
//        } else if (userDTO.getOperation().equals(OperationRoles.REVOKE.toString()) && userDTO.getRole().equals(ADMINISTRATOR.toString())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CANNOT_REMOVE_ADMINISTRATOR);
//        } else if (userDTO.getOperation().equals(OperationRoles.REVOKE.toString()) && userByEmail.getAuthorities().size() == 1) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, LAST_ROLE);
//        } else if (userDTO.getOperation().equals(GRANT.toString()) && userByEmail.getAuthorities().contains(ADMINISTRATOR)
//                || !userByEmail.getAuthorities().contains(ADMINISTRATOR) && userDTO.getRole().equals(ADMINISTRATOR.toString())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NOT_COMBINE_ADMINISTRATIVE_AND_BUSINESS_ROLES);
//        }
//    }

//    @Override
//    public Map<String, String> lockOrUnlockUser(LockUserDTO userDTO) {
//        User userByEmail = userRepository.findByEmailIgnoreCase(userDTO.getEmail())
//                .orElseThrow(EmployeeNotFoundException::new);
//        if (userByEmail.getAuthorities().contains(ADMINISTRATOR)) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CANNOT_LOCK_ADMINISTRATOR);
//        }
//
//        String object;
//        UserDetails auth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (userDTO.getOperation().equals(LOCK.toString()) && userByEmail.isAccountNonLocked()) {
//            userByEmail.setAccountNonLocked(false);
//            userRepository.save(userByEmail);
//            object = String.format("Lock user %s", userByEmail.getEmail());
//            securityEventsService.registerEvent(LOCK_USER, auth.getUsername(), object, "/api/admin/user/access");
//            LOGGER.info(String.format("User %s was LOCKED", userByEmail.getEmail()));
//        } else if (userDTO.getOperation().equals(OperationLocks.UNLOCK.toString()) && !userByEmail.isAccountNonLocked()) {
//            userByEmail.setAccountNonLocked(true);
//            userByEmail.setFailedAttempt(0);
//            userRepository.save(userByEmail);
//            object = String.format("Unlock user %s", userByEmail.getEmail());
//            securityEventsService.registerEvent(UNLOCK_USER, auth.getUsername(), object, "/api/admin/user/access");
//            LOGGER.info(String.format("User %s was UNLOCKED", userByEmail.getEmail()));
//        } else {
//            return Map.of("status", SMTH_WRONG);
//        }
//        return Map.of("status", String.format("User %s %sed!", userDTO.getEmail().toLowerCase(), userDTO.getOperation().toLowerCase()));
//    }

//    @Override
//    public void increaseFailedAttempts(User user, String path) {
//        user.setFailedAttempt(user.getFailedAttempt() + 1);
//        if (user.getFailedAttempt() > MAX_FAILED_ATTEMPTS)
//            lockUser(user, path);
//        userRepository.save(user);
//    }
//
//    public void lockUser(User user, String path) {
//        user.setAccountNonLocked(false);
//        securityEventsService.registerEvent(BRUTE_FORCE, user.getEmail(), path, path);
//        securityEventsService.registerEvent(LOCK_USER, user.getEmail(), String.format("Lock user %s", user.getEmail()), path);
//        LOGGER.info(String.format(USER_LOCKED_BRUTE, user.getEmail()));
//    }
}

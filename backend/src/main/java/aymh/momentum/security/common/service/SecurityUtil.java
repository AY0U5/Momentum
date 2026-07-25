package aymh.momentum.security.common.service;

import aymh.momentum.security.bean.User;
import aymh.momentum.security.service.facade.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SecurityUtil {

    private final UserService userService;

    public User getCurrentUser(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object user = Objects.requireNonNull(securityContext.getAuthentication()).getPrincipal();
        if (user instanceof String) {
            return userService.findByUsername((String) user).orElseThrow(
                    ()->new IllegalStateException("User Not Found")
            );
        } else if (user instanceof User) {
            return (User) user;
        } else {
            return null;
        }
    }
}

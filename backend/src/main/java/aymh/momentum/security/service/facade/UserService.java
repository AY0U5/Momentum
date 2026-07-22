package aymh.momentum.security.service.facade;

import aymh.momentum.security.bean.User;
import aymh.momentum.security.ws.dto.GoogleAuthRequest;
import aymh.momentum.security.ws.dto.LoginRequest;
import jakarta.mail.MessagingException;

public interface UserService {
    String createUser(User user) throws MessagingException;

    String signIn(LoginRequest request);

    String signInWithGoogle(GoogleAuthRequest request);
}

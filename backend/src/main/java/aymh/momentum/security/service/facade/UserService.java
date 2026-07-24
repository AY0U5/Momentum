package aymh.momentum.security.service.facade;

import aymh.momentum.security.bean.User;
import aymh.momentum.security.ws.dto.GoogleAuthRequest;
import aymh.momentum.security.ws.dto.LoginRequest;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {
    String createUser(User user) throws MessagingException;

    String signIn(LoginRequest request);

    String signInWithGoogle(GoogleAuthRequest request);

    String uploadProfilePicture(Long userId, MultipartFile file);

    Optional<User> findById(Long userId);
}

package aymh.momentum.security.service.impl;

import aymh.momentum.security.bean.Role;
import aymh.momentum.security.bean.User;
import aymh.momentum.security.common.enums.EmailTemplateName;
import aymh.momentum.security.common.service.MailService;
import aymh.momentum.security.dao.RoleDao;
import aymh.momentum.security.dao.UserDao;
import aymh.momentum.security.exceptions.UserAlreadyExistException;
import aymh.momentum.security.service.facade.UserService;

import aymh.momentum.security.ws.dto.GoogleAuthRequest;
import aymh.momentum.security.ws.dto.LoginRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao dao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;

    @Value("${spring.security.oauth2.client.registration.google.client-id:YOUR_GOOGLE_CLIENT_ID}")
    private String googleClientId;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public String createUser(User user) throws MessagingException {
        if (user == null) {
            throw new IllegalArgumentException("The user should not be null");
        }
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("The username is mandatory");
        }
        if (!EMAIL_PATTERN.matcher(user.getUsername()).matches()) {
            throw new IllegalArgumentException("Email invalid");
        }
        if (dao.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("User with email " + user.getUsername() + " already exist.");
        }

        boolean isLocalUser = user.getProvider() == null || "LOCAL".equalsIgnoreCase(user.getProvider());
        if (isLocalUser) {
            if (user.getPassword() == null || user.getPassword().length() < 6) {
                throw new IllegalArgumentException("Password should have minimum 6 character");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setProvider("LOCAL");
        }

        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        if (user.getRole() == null) {
            Role defaultRole = roleDao.findByAuthority("ROLE_USER")
                    .orElseThrow(() -> new IllegalStateException("Role User not found"));
            user.setRole(defaultRole);
        }

        dao.save(user);
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", user.getNickname());
        variables.put("email", user.getUsername());

        mailService.sendEmailWithTemplate(
                user.getUsername(),
                EmailTemplateName.ACCOUNT_CREATED,
                variables,
                "Welcome to Momentum!"
        );
        return jwtService.generateToken(user);
    }

    @Override
    public String signIn(LoginRequest request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Username and password are required.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = dao.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        return jwtService.generateToken(user);
    }

    @Override
    public String signInWithGoogle(GoogleAuthRequest request) {
        if (request.getIdToken() == null || request.getIdToken().isBlank()) {
            throw new IllegalArgumentException("Google ID Token is required.");
        }

        try {
            // 1. Vérification du token d'ID auprès de Google
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(request.getIdToken());
            if (idToken == null) {
                throw new BadCredentialsException("Invalid Google Token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");

            // 2. Recherche ou Création automatique du compte utilisateur
            User user = dao.findByUsername(email).orElseGet(() -> {
                Role defaultRole = roleDao.findByAuthority("ROLE_USER")
                        .orElseThrow(() -> new IllegalStateException("Role User not found"));

                User newUser = new User();
                newUser.setUsername(email);
                newUser.setNickname(name);
                newUser.setPicture(pictureUrl);
                newUser.setProvider("GOOGLE");
                newUser.setEnabled(true);
                newUser.setAccountNonExpired(true);
                newUser.setAccountNonLocked(true);
                newUser.setCredentialsNonExpired(true);
                newUser.setCreatedAt(LocalDateTime.now());
                newUser.setUpdatedAt(LocalDateTime.now());
                newUser.setRole(defaultRole);

                return dao.save(newUser);
            });
            return jwtService.generateToken(user);

        } catch (GeneralSecurityException | IOException e) {
            throw new BadCredentialsException("Failed to authenticate with Google: " + e.getMessage());
        }
    }
}
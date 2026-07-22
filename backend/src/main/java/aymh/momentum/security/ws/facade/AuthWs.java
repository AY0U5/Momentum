package aymh.momentum.security.ws.facade;

import aymh.momentum.security.bean.User;
import aymh.momentum.security.service.facade.UserService;
import aymh.momentum.security.ws.converter.UserConverter;
import aymh.momentum.security.ws.dto.GoogleAuthRequest;
import aymh.momentum.security.ws.dto.LoginRequest;
import aymh.momentum.security.common.dto.Response;
import aymh.momentum.security.ws.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthWs {

    private final UserService userService;
    private final UserConverter converter;

    @PostMapping("/create")
    public ResponseEntity<Response<String>> createUser(@RequestBody @Valid UserDto req) {
        try {
            User user = converter.toBean(req);
            String token = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new Response<>("User created successfully",true,token)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>(e.getMessage(),false,null)
            );
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Response<String>> signIn(@RequestBody @Valid LoginRequest request) {
        try {
            String token = userService.signIn(request);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Response<>("User sign in successfully",true,token)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>(e.getMessage(),false,null)
            );
        }
    }

    @PostMapping("/google-login")
    public ResponseEntity<Response<String>> signInWithGoogle(@RequestBody @Valid GoogleAuthRequest request) {
        try {
            String token = userService.signInWithGoogle(request);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Response<>("User sign in with google successfully",true,token)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>(e.getMessage(),false,null)
            );
        }
    }
}

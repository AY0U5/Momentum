package aymh.momentum.security.ws.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @Email
    private String username;
    @Size(min = 6)
    private String password;
}

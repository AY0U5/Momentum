package aymh.momentum.security.ws.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    @NotBlank
    @Email
    private String username;//email
    @NotBlank
    private String nickname;
    @Size(min = 6)
    private String password;
    private String provider; //Google or Local
    private String picture;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private RoleDto role;
}

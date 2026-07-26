package aymh.momentum.ws.dto;

import aymh.momentum.security.common.enums.ProjectRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String username;
    private String nickname;
    private String picture;
    private ProjectRole role;
    private LocalDateTime joinedAt;
}

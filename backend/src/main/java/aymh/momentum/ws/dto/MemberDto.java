package aymh.momentum.ws.dto;

import aymh.momentum.security.common.enums.ProjectRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MemberDto {
    private Long id;
    private String username;
    private String nickname;
    private String picture;
    private ProjectRole role;
    private LocalDateTime joinedAt;
}

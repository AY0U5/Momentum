package aymh.momentum.ws.dto;

import aymh.momentum.security.common.enums.InvitationState;
import aymh.momentum.security.ws.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private InvitationState state;
    private UserDto sender;
    private UserDto receiver;
    private ProjectDto project;

}

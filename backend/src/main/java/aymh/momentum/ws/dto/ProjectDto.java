package aymh.momentum.ws.dto;

import aymh.momentum.security.common.enums.ProjectState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    @NotBlank()
    private String title;
    @Size(max = 1000)
    private String description;
    private LocalDateTime createdAt;
    private ProjectState state;
    private Boolean pinned;
    private Long memberCount;
    private List<MemberDto> members;
}

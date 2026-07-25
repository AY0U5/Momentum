package aymh.momentum.ws.dto;

import aymh.momentum.security.common.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TaskDto {

    private Long id;
    @NotBlank()
    @Size(max = 150)
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private TaskPriority priority;
    private KanbanColumnDto column;
    private List<MemberDto> assignees;
    private List<AttachmentDto> attachments;
}

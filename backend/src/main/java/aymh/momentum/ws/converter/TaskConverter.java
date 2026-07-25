package aymh.momentum.ws.converter;

import aymh.momentum.bean.Task;
import aymh.momentum.ws.dto.AttachmentDto;
import aymh.momentum.ws.dto.MemberDto;
import aymh.momentum.ws.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskConverter {

    private final KanbanConverter kanbanConverter;

    public Task toBean(TaskDto dto) {
        if (dto == null) {
            return null;
        }
        return Task.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .dueDate(dto.getDueDate())
                .priority(dto.getPriority())
                .column(kanbanConverter.toBean(dto.getColumn()))
                .build();
    }

    public TaskDto toDto(Task bean) {
        if (bean == null) {
            return null;
        }
        return TaskDto.builder()
                .id(bean.getId())
                .title(bean.getTitle())
                .description(bean.getDescription())
                .dueDate(bean.getDueDate())
                .priority(bean.getPriority())
                .column(kanbanConverter.toDto(bean.getColumn()))
                .build();
    }

    public TaskDto toDto(Task bean, List<MemberDto> assignees , List<AttachmentDto> attachments) {
        if (bean == null) {
            return null;
        }
        return TaskDto.builder()
                .id(bean.getId())
                .title(bean.getTitle())
                .description(bean.getDescription())
                .dueDate(bean.getDueDate())
                .priority(bean.getPriority())
                .column(kanbanConverter.toDto(bean.getColumn()))
                .assignees(assignees)
                .attachments(attachments)
                .build();
    }
}

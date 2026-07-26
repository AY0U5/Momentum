package aymh.momentum.ws.converter;

import aymh.momentum.bean.KanbanColumn;
import aymh.momentum.ws.dto.KanbanColumnDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KanbanConverter {

    private final ProjectConverter projectConverter;

    public KanbanColumn toBean(KanbanColumnDto dto) {
        if (dto == null) {
            return null;
        }
        return KanbanColumn.builder()
                .id(dto.getId())
                .label(dto.getLabel())
                .position(dto.getPosition())
                .color(dto.getColor())
                .project(projectConverter.toBean(dto.getProject()))
                .build();
    }

    public KanbanColumnDto toDto(KanbanColumn bean) {
        if (bean == null) {
            return null;
        }
        return KanbanColumnDto.builder()
                .id(bean.getId())
                .label(bean.getLabel())
                .position(bean.getPosition())
                .color(bean.getColor())
                .project(projectConverter.toDto(bean.getProject()))
                .build();
    }
}

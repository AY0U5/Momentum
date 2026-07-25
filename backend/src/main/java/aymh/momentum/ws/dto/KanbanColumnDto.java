package aymh.momentum.ws.dto;

import aymh.momentum.security.common.enums.ColumnNames;
import lombok.*;

@Data
@Builder
public class KanbanColumnDto {
    private Long id;
    private ColumnNames label;
    private Long position;
    private String color;
    private ProjectDto project;
}

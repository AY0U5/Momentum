package aymh.momentum.ws.converter;

import aymh.momentum.bean.KanbanColumn;
import aymh.momentum.ws.dto.KanbanColumnDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KanbanConverter {


    public KanbanColumn toBean(KanbanColumnDto dto) {
        if (dto == null) {
            return null;
        }
        return null;
    }

    public KanbanColumnDto toDto(KanbanColumn bean) {
        if (bean == null) {
            return null;
        }
        return null;
    }
}

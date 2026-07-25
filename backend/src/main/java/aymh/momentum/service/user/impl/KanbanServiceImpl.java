package aymh.momentum.service.user.impl;

import aymh.momentum.bean.KanbanColumn;
import aymh.momentum.bean.Project;
import aymh.momentum.dao.KanbanDao;
import aymh.momentum.security.common.enums.ColumnNames;
import aymh.momentum.service.user.facade.KanbanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KanbanServiceImpl implements KanbanService {

    private final KanbanDao kanbanDao;

    @Override
    public void initialize(Project saved) {
        KanbanColumn todo = KanbanColumn.builder()
                .label(ColumnNames.TO_DO)
                .color("#FF5733")
                .project(saved)
                .position(1L)
                .build();

        KanbanColumn inProgress = KanbanColumn.builder()
                .label(ColumnNames.IN_PROGRESS)
                .color("#3357FF")
                .project(saved)
                .position(2L)
                .build();

        KanbanColumn done = KanbanColumn.builder()
                .label(ColumnNames.DONE)
                .color("#28A745")
                .project(saved)
                .position(3L)
                .build();

        kanbanDao.saveAll(List.of(todo,inProgress,done));
    }

    @Override
    public Optional<KanbanColumn> findById(Long id) {
        return kanbanDao.findById(id);
    }

    @Override
    public Optional<KanbanColumn> findFirstByProjectIdOrderByPositionAsc(Long id) {
        return kanbanDao.findFirstByProjectIdOrderByPositionAsc(id);
    }
}

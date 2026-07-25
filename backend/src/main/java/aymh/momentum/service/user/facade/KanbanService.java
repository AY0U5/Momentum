package aymh.momentum.service.user.facade;

import aymh.momentum.bean.KanbanColumn;
import aymh.momentum.bean.Project;

import java.util.Optional;

public interface KanbanService {
    void initialize(Project saved);

    Optional<KanbanColumn> findById(Long id);

    Optional<KanbanColumn> findFirstByProjectIdOrderByPositionAsc(Long id);
}

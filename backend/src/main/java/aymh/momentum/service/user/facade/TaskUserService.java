package aymh.momentum.service.user.facade;

import aymh.momentum.bean.TaskUser;

public interface TaskUserService {
    boolean existsByTaskIdAndAssigneeId(Long taskId, Long assigneeId);

    void deleteByTaskIdAndAssigneeId(Long taskId, Long assigneeId);

    TaskUser save(TaskUser entity);
}

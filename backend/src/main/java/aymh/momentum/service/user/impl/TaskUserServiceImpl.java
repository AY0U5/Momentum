package aymh.momentum.service.user.impl;

import aymh.momentum.bean.TaskUser;
import aymh.momentum.dao.TaskUserDao;
import aymh.momentum.service.user.facade.TaskUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskUserServiceImpl implements TaskUserService {

    private final TaskUserDao taskUserDao;

    @Override
    public boolean existsByTaskIdAndAssigneeId(Long taskId, Long assigneeId) {
        return taskUserDao.existsByTaskIdAndAssigneeId(taskId, assigneeId);
    }

    @Override
    public void deleteByTaskIdAndAssigneeId(Long taskId, Long assigneeId) {
        taskUserDao.deleteByTaskIdAndAssigneeId(taskId, assigneeId);
    }

    @Override
    public TaskUser save(TaskUser entity) {
        return taskUserDao.save(entity);
    }
}

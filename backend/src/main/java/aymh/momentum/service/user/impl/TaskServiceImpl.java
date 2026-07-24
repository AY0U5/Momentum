package aymh.momentum.service.user.impl;

import aymh.momentum.bean.Task;
import aymh.momentum.dao.TaskDao;
import aymh.momentum.service.user.facade.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;

    @Override
    public Optional<Task> findById(Long taskId) {
        return taskDao.findById(taskId);
    }
}

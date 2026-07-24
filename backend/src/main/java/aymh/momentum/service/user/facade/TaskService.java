package aymh.momentum.service.user.facade;

import aymh.momentum.bean.Task;

import java.util.Optional;

public interface TaskService {
    Optional<Task> findById(Long taskId);
}

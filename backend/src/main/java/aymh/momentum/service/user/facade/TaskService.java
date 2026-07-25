package aymh.momentum.service.user.facade;

import aymh.momentum.bean.KanbanColumn;
import aymh.momentum.bean.Task;
import aymh.momentum.bean.TaskUser;
import aymh.momentum.security.bean.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface TaskService {
    Task createTask(Task req, Long id);

    Task moveTask(Long id, KanbanColumn column);

    void addAttachment(Long taskId, MultipartFile file);

    TaskUser assignTask(Long taskId, User user);

    Optional<Task> findById(Long taskId);
}

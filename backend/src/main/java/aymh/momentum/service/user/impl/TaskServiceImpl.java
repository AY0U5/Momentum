package aymh.momentum.service.user.impl;

import aymh.momentum.bean.KanbanColumn;
import aymh.momentum.bean.Membership;
import aymh.momentum.bean.Task;
import aymh.momentum.bean.TaskUser;
import aymh.momentum.dao.TaskDao;
import aymh.momentum.security.bean.User;
import aymh.momentum.security.common.enums.ProjectRole;
import aymh.momentum.security.common.enums.TaskPriority;
import aymh.momentum.security.common.service.SecurityUtil;
import aymh.momentum.service.user.facade.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;
    private final KanbanService kanbanService;
    private final MembershipService membershipService;
    private final AttachmentService attachmentService;
    private final SecurityUtil util;
    private final TaskUserService taskUserService;

    @Transactional
    @Override
    public Task createTask(Task req, Long id){
        User currentUser = util.getCurrentUser();
        Membership membership = membershipService.findByUserAndProjectIdAndActiveTrue(currentUser, id)
                .orElseThrow(() -> new IllegalStateException("Your aren't member in this project"));
        if (membership.getRole() == ProjectRole.VIEWER) {
            throw new IllegalStateException("Viewers can't create tasks");
        }
        KanbanColumn column;
        if (req.getColumn() != null) {
            column = kanbanService.findById(req.getColumn().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Column not found"));
        } else {
            column = kanbanService.findFirstByProjectIdOrderByPositionAsc(id)
                    .orElseThrow(() -> new IllegalStateException("No Column in this project"));
        }

        Task task = Task.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .dueDate(req.getDueDate())
                .column(column)
                .priority(req.getPriority() != null ? req.getPriority() : TaskPriority.MEDIUM )
                .build();

        return taskDao.save(task);
    }

    @Override
    public Task moveTask(Long id, KanbanColumn column){
        User currentUser = util.getCurrentUser();

        Task task = taskDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task Not Found"));

        Long projectId = task.getColumn().getProject().getId();
        Membership membership = membershipService.findByUserAndProjectIdAndActiveTrue(currentUser, projectId)
                .orElseThrow(() -> new IllegalStateException("Your aren't member in this project"));

        if (membership.getRole() == ProjectRole.VIEWER) {
            throw new IllegalStateException("Viewers can't move tasks");
        }

        KanbanColumn targetColumn = kanbanService.findById(column.getId())
                .orElseThrow(() -> new IllegalArgumentException("Column Not Found"));

        task.setColumn(targetColumn);
        return taskDao.save(task);
    }


    @Transactional
    @Override
    public void addAttachment(Long taskId, MultipartFile file){
        User currentUser = util.getCurrentUser();
        Task task = findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        Long projectId = task.getColumn().getProject().getId();
        membershipService.findByUserAndProjectIdAndActiveTrue(currentUser, projectId)
                .orElseThrow(() -> new IllegalStateException("Your aren't member in this project"));
        attachmentService.addAttachmentToTask(task,currentUser,file);
    }

    @Transactional
    @Override
    public void assignTask(Long taskId, User user) {
        User currentUser = util.getCurrentUser();
        Task task = taskDao.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task Not Found"));

        Long projectId = task.getColumn().getProject().getId();
        membershipService.findByUserIdAndProjectIdAndActiveTrue(currentUser.getId(), projectId)
                .orElseThrow(() -> new IllegalStateException("Your aren't member in this project"));

        Membership targetMembership = membershipService.findByUserAndProjectIdAndActiveTrue(user, projectId)
                .orElseThrow(() -> new IllegalArgumentException("The assignee isn't member in this project"));

        if (taskUserService.existsByTaskIdAndAssigneeId(taskId, user.getId())) {
            throw new IllegalStateException("User already assigned to this task");
        }

        TaskUser taskUser = TaskUser.builder()
                .task(task)
                .assignee(targetMembership.getUser())
                .build();

        taskUserService.save(taskUser);
    }

    @Override
    public Optional<Task> findById(Long taskId) {
        return taskDao.findById(taskId);
    }
}

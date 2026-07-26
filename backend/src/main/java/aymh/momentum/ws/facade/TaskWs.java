package aymh.momentum.ws.facade;

import aymh.momentum.security.bean.User;
import aymh.momentum.security.common.dto.Response;
import aymh.momentum.service.user.facade.TaskService;
import aymh.momentum.ws.converter.KanbanConverter;
import aymh.momentum.ws.converter.MemberConverter;
import aymh.momentum.ws.converter.TaskConverter;
import aymh.momentum.ws.dto.KanbanColumnDto;
import aymh.momentum.ws.dto.TaskDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskWs {

    private final TaskService service;
    private final TaskConverter converter;
    private final KanbanConverter kanbanConverter;
    private final MemberConverter memberConverter;

    @PostMapping("/project/{id}")
    public ResponseEntity<Response<TaskDto>> createTask(@RequestBody TaskDto req,@PathVariable Long id) {
        try {
            List<User> assignee = req.getAssignees().stream().map(memberConverter::toUser).toList();
            TaskDto task = converter.toDto(service.createTask(converter.toBean(req), id));
            for (User user: assignee){
                service.assignTask(task.getId(),user);
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Response<>("Task Created Successfully",true,task)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>(e.getMessage(), false,null)
            );
        }
    }

    @PostMapping("/{id}/move")
    public ResponseEntity<Response<TaskDto>> moveTask(@PathVariable Long id, @RequestBody KanbanColumnDto column) {
        try {
            TaskDto task = converter.toDto(service.moveTask(id, kanbanConverter.toBean(column)));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Response<>("Task Moved Successfully",true,task)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>(e.getMessage(), false,null)
            );
        }
    }

    @PostMapping("/{taskId}/attachments")
    public ResponseEntity<Response<Void>> addAttachment(@PathVariable Long taskId, @RequestParam("file") MultipartFile file) {
        try {
            service.addAttachment(taskId, file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Response<>("Attachment uploaded Successfully",true,null)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>(e.getMessage(), false,null)
            );
        }
    }
}

package aymh.momentum.ws.facade;

import aymh.momentum.bean.Project;
import aymh.momentum.security.bean.User;
import aymh.momentum.security.common.dto.Response;
import aymh.momentum.security.common.enums.ProjectRole;
import aymh.momentum.service.user.facade.ProjectService;
import aymh.momentum.ws.converter.MemberConverter;
import aymh.momentum.ws.converter.ProjectConverter;
import aymh.momentum.ws.dto.MemberDto;
import aymh.momentum.ws.dto.ProjectDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectWs {

    private final ProjectService service;
    private final ProjectConverter converter;
    private final MemberConverter memberConverter;

    @PostMapping("/")
    public ResponseEntity<Response<ProjectDto>> createProject(@RequestBody ProjectDto project) {
        try {
            ProjectDto saved = converter.toDto(service.createProject(converter.toBean(project), project.getPinned()));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Response<>("Project Created Successfully",true,saved)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>(e.getMessage(), false,null)
            );
        }
    }

    @GetMapping("/")
    public ResponseEntity<Response<List<ProjectDto>>> getUserProjects() {
        try {
            List<ProjectDto> userProjects = service.getUserProjects().stream().map(converter::toDto).toList();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Response<>("User Projects loaded successfully",true,userProjects)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>(e.getMessage(), false,null)
            );
        }
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<Response<ProjectDto>> getProjectDetails(@PathVariable Long id) {
        try {
            Map<Project, Boolean> projectDetails = service.getProjectDetails(id);
            Map<User, ProjectRole> projectMembers = service.getProjectMembers(id);
            if (projectDetails == null || projectDetails.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new Response<>("Project Not Found",false,null)
                );
            }
            Map.Entry<Project, Boolean> entry = projectDetails.entrySet().iterator().next();
            Project project = entry.getKey();
            Boolean isPinned = entry.getValue();
            List<MemberDto> memberDtos = projectMembers.entrySet().stream()
                    .map(e -> {
                        User user = e.getKey();
                        ProjectRole role = e.getValue();
                        return memberConverter.toMember(user,role);
                    })
                    .toList();
            long memberCount = memberDtos.size();
            ProjectDto dto = converter.toDto(project, Boolean.TRUE.equals(isPinned), memberCount, memberDtos);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Response<>("Project details loaded successfully",true,dto)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>(e.getMessage(), false,null)
            );
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Response<Void>> deleteProject(@PathVariable Long id) {
        try {
            service.deleteProject(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Response<>("Project deleted successfully",true,null)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response<>(e.getMessage(), false,null)
            );
        }
    }
}

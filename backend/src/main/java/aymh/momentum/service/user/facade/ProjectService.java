package aymh.momentum.service.user.facade;

import aymh.momentum.bean.Project;
import aymh.momentum.security.bean.User;
import aymh.momentum.security.common.enums.ProjectRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProjectService {
    Project createProject(Project project, boolean pinned);

    List<Project> getUserProjects();


    Map<Project,Boolean> getProjectDetails(Long id);

    Map<User, ProjectRole> getProjectMembers(Long id);

    void deleteProject(Long id);

    Optional<Project> findById(Long aLong);
}

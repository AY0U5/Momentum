package aymh.momentum.service.user.impl;

import aymh.momentum.bean.Membership;
import aymh.momentum.bean.Project;
import aymh.momentum.dao.ProjectDao;
import aymh.momentum.security.bean.User;
import aymh.momentum.security.common.enums.ProjectRole;
import aymh.momentum.security.common.enums.ProjectState;
import aymh.momentum.security.common.service.SecurityUtil;
import aymh.momentum.service.user.facade.KanbanService;
import aymh.momentum.service.user.facade.MembershipService;
import aymh.momentum.service.user.facade.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectDao projectDao;
    private final SecurityUtil util;
    private final MembershipService membershipService;
    private final KanbanService kanbanService;

    @Transactional
    @Override
    public Project createProject(Project project, boolean pinned){
        User current = util.getCurrentUser();
        if (current == null) {
            throw new IllegalStateException("User No Authenticated");
        }
        if (project == null || project.getTitle() == null || project.getTitle().isBlank()) {
            throw new IllegalArgumentException("The title is mandatory");
        }
        Project saved = Project.builder()
                .title(project.getTitle())
                .description(project.getDescription())
                .createdAt(LocalDateTime.now())
                .state(ProjectState.PLANNED)
                .build();
        projectDao.save(saved);
        membershipService.createOwner(current,saved,pinned);
        kanbanService.initialize(saved);
        return saved;
    }

    @Override
    public List<Project> getUserProjects(){
        User currentUser = util.getCurrentUser();
        List<Membership> memberships = membershipService.findAllActiveMembershipsByUser(currentUser);
        return memberships.stream().map(
                Membership::getProject
        ).toList();
    }

    @Override
    public Map<Project,Boolean> getProjectDetails(Long id){
        User currentUser = util.getCurrentUser();
        Membership currentMembership = membershipService.findByUserAndProjectIdAndActiveTrue(currentUser, id)
                .orElseThrow(
                        ()-> new IllegalStateException("You aren't a member in this project")
                );

        return Map.of(
                currentMembership.getProject(),
                currentMembership.isPinned()
        );
    }

    @Override
    public Map<User, ProjectRole> getProjectMembers(Long id){
        User currentUser = util.getCurrentUser();
        Membership currentMembership = membershipService.findByUserAndProjectIdAndActiveTrue(currentUser, id)
                .orElseThrow(
                        ()-> new IllegalStateException("You aren't a member in this project")
                );

        Project project = currentMembership.getProject();
        List<Membership> projectMemberships = membershipService.findAllByProjectIdAndActiveTrue(project.getId());
        return projectMemberships.stream().collect(
                Collectors.toMap(
                        Membership::getUser,
                        Membership::getRole
                )
        );
    }

    @Transactional
    @Override
    public void deleteProject(Long id){
        User currentUser = util.getCurrentUser();
        Membership currentMembership = membershipService.findByUserAndProjectIdAndActiveTrue(currentUser, id)
                .orElseThrow(
                        ()-> new IllegalStateException("You aren't a member in this project")
                );

        if (currentMembership.getRole() != ProjectRole.OWNER) {
            throw new IllegalStateException("Only the Owner can delete the project");
        }
        projectDao.deleteById(id);
    }

    @Override
    public Optional<Project> findById(Long aLong) {
        return projectDao.findById(aLong);
    }
}

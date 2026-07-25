package aymh.momentum.service.user.facade;

import aymh.momentum.bean.Membership;
import aymh.momentum.bean.Project;
import aymh.momentum.security.bean.User;
import aymh.momentum.security.common.enums.ProjectRole;

import java.util.List;
import java.util.Optional;

public interface MembershipService {
    void createOwner(User current, Project saved, boolean pinned);

    Optional<Membership> findByUserAndProjectAndActiveTrue(User user, Project project);

    void createMember(User receiver, Project project);

    void removeMember(Project project, User user);

    void changeRole(Project project, User target, ProjectRole newRole);

    boolean togglePinProject(Project project);

    List<Membership> findAllActiveMembershipsByUser(User user);

    List<Membership> findAllByProjectIdAndActiveTrue(Long projectId);

    long countByProjectIdAndActiveTrue(Long projectId);

    Optional<Membership> findByUserAndProjectIdAndActiveTrue(User currentUser, Long id);
}

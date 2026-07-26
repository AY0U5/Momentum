package aymh.momentum.service.user.impl;

import aymh.momentum.bean.Membership;
import aymh.momentum.bean.Project;
import aymh.momentum.dao.MembershipDao;
import aymh.momentum.security.bean.User;
import aymh.momentum.security.common.enums.ProjectRole;
import aymh.momentum.security.common.service.SecurityUtil;
import aymh.momentum.service.user.facade.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipDao membershipDao;
    private final SecurityUtil util;

    @Override
    public void createOwner(User current, Project saved, boolean pinned) {
        Membership membership = Membership.builder()
                .active(true)
                .pinned(pinned)
                .role(ProjectRole.OWNER)
                .project(saved)
                .joinedAt(LocalDateTime.now())
                .user(current)
                .build();
        membershipDao.save(membership);
    }

    @Override
    public Optional<Membership> findByUserAndProjectAndActiveTrue(User user, Project project) {
        return membershipDao.findByUserAndProjectAndActiveTrue(user, project);
    }

    @Override
    public void createMember(User receiver, Project project) {
        boolean exists = membershipDao.findByUserAndProjectAndActiveTrue(receiver, project).isPresent();
        if (exists) {
            return;
        }
        Membership membership = Membership.builder()
                .active(true)
                .pinned(false)
                .role(ProjectRole.MEMBER)
                .project(project)
                .joinedAt(LocalDateTime.now())
                .user(receiver)
                .build();
        membershipDao.save(membership);
    }

    @Transactional
    @Override
    public void removeMember(Project project, User user){
        User current = util.getCurrentUser();
        Membership currentMembership = findByUserAndProjectAndActiveTrue(current,project).orElseThrow(
                ()-> new IllegalStateException("You aren't a member in this project")
        );

        Membership targetMembership = findByUserAndProjectAndActiveTrue(user, project)
                .orElseThrow(() -> new IllegalStateException("Target user isn't a member in this project"));

        if (targetMembership.getRole() == ProjectRole.OWNER) {
            throw new IllegalStateException("You can't delete the owner");
        }
        if (currentMembership.getRole() == ProjectRole.MEMBER || currentMembership.getRole() == ProjectRole.VIEWER) {
            throw new IllegalStateException("You don't have the permission to remove member");
        }
        if (currentMembership.getRole() == ProjectRole.ADMIN && targetMembership.getRole() == ProjectRole.ADMIN) {
            throw new IllegalStateException("An admin can't remove an admin");
        }
        targetMembership.setActive(false);
        membershipDao.save(targetMembership);
    }

    @Transactional
    @Override
    public void changeRole(Project project, User target, ProjectRole newRole){
        User current = util.getCurrentUser();
        if (current.equals(target)) {
            throw new IllegalArgumentException("You can't change your role");
        }
        Membership currentMembership = findByUserAndProjectAndActiveTrue(current,project).orElseThrow(
                ()-> new IllegalStateException("You aren't a member in this project")
        );

        if (currentMembership.getRole() != ProjectRole.OWNER) {
            throw new IllegalStateException("Only the owner can change roles");
        }

        Membership targetMembership = findByUserAndProjectAndActiveTrue(target,project).orElseThrow(
                ()-> new IllegalStateException("The target isn't a member in this project")
        );

        if (newRole == ProjectRole.OWNER) {
            currentMembership.setRole(ProjectRole.ADMIN);
            membershipDao.save(currentMembership);
        }
        targetMembership.setRole(newRole);
        membershipDao.save(targetMembership);
    }

    @Override
    public boolean togglePinProject(Project project) {
        User currentUser = util.getCurrentUser();

        Membership membership = findByUserAndProjectAndActiveTrue(currentUser, project)
                .orElseThrow(() -> new IllegalStateException("Project Not Found"));

        boolean newPinnedState = !membership.isPinned();
        membership.setPinned(newPinnedState);
        membershipDao.save(membership);

        return newPinnedState;
    }

    @Override
    public List<Membership> findAllActiveMembershipsByUser(User user) {
        return membershipDao.findAllActiveMembershipsByUser(user);
    }

    @Override
    public List<Membership> findAllByProjectIdAndActiveTrue(Long projectId) {
        return membershipDao.findAllByProjectIdAndActiveTrue(projectId);
    }

    @Override
    public long countByProjectIdAndActiveTrue(Long projectId) {
        return membershipDao.countByProjectIdAndActiveTrue(projectId);
    }

    @Override
    public Optional<Membership> findByUserAndProjectIdAndActiveTrue(User currentUser, Long id) {
        return membershipDao.findByUserAndProjectIdAndActiveTrue(currentUser,id);
    }

    @Override
    public Optional<Membership> findByUserIdAndProjectIdAndActiveTrue(Long id, Long projectId) {
        return membershipDao.findByUserIdAndProjectIdAndActiveTrue(id,projectId);
    }
}

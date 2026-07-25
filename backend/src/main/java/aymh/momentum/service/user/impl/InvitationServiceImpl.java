package aymh.momentum.service.user.impl;

import aymh.momentum.bean.Invitation;
import aymh.momentum.bean.Membership;
import aymh.momentum.bean.Project;
import aymh.momentum.dao.InvitationDao;
import aymh.momentum.security.bean.User;
import aymh.momentum.security.common.enums.EmailTemplateName;
import aymh.momentum.security.common.enums.InvitationState;
import aymh.momentum.security.common.enums.ProjectRole;
import aymh.momentum.security.common.service.MailService;
import aymh.momentum.security.common.service.SecurityUtil;
import aymh.momentum.security.service.facade.UserService;
import aymh.momentum.service.user.facade.InvitationService;
import aymh.momentum.service.user.facade.MembershipService;
import aymh.momentum.service.user.facade.ProjectService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationDao invitationDao;
    private final SecurityUtil util;
    private final ProjectService projectService;
    private final MembershipService membershipService;
    private final UserService userService;
    private final MailService mailService;

    @Transactional
    @Override
    public Invitation sendInvitation(Invitation request) throws MessagingException {
        User sender = util.getCurrentUser();
        if (sender == null) {
            throw new IllegalStateException("User No Authenticated");
        }

        Project project = projectService.findById(request.getProject().getId()).orElseThrow(
                ()->new IllegalStateException("Project Not Found")
        );

        Membership senderMembership =
                membershipService.findByUserAndProjectAndActiveTrue(sender, project).orElseThrow(
                        ()-> new IllegalStateException("You aren't a member")
                );
        if (
                !senderMembership.isActive()
                || senderMembership.getRole() == ProjectRole.MEMBER
                || senderMembership.getRole() == ProjectRole.VIEWER
        ) {
            throw new IllegalStateException("You don't have the permission to invite");
        }

        User receiver = userService.findByUsername(request.getReceiver().getUsername()).orElseThrow(
                ()-> new IllegalStateException("Receiver Not Found")
        );
        if (membershipService.findByUserAndProjectAndActiveTrue(receiver,project).isPresent()) {
            throw new IllegalStateException("Receiver Already a member in this project");
        }

        boolean hasPendingInvitation = invitationDao.existsBySenderAndReceiverAndProjectAndExpiredAtAfter(
                sender, receiver, project, LocalDateTime.now()
        );
        if (hasPendingInvitation) {
            throw new IllegalArgumentException("An Invitation has sent to this user pending");
        }

        Invitation invitation = Invitation.builder()
                .sender(sender)
                .receiver(receiver)
                .project(project)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusDays(7))
                .state(InvitationState.PENDING)
                .build();

        Invitation saved = invitationDao.save(invitation);

        Map<String, Object> variables = new HashMap<>();
        variables.put("receiverName", receiver.getNickname() != null ? receiver.getNickname() : receiver.getUsername());
        variables.put("senderName", sender.getNickname() != null ? sender.getNickname() : sender.getUsername());
        variables.put("projectName", project.getTitle());
        variables.put("projectDescription", project.getDescription() != null ? project.getDescription() : "Without Description");
        variables.put("expirationDays", 7);
        mailService.sendEmailWithTemplate(
                receiver.getUsername(),
                EmailTemplateName.SEND_INVITATION,
                variables,
                "Invitation to join "+ project.getTitle()
        );
        return saved;
    }

    @Override
    public void acceptInvitation(Invitation request){
        User receiver = util.getCurrentUser();
        if (receiver == null) {
            throw new IllegalStateException("User Not Authenticated");
        }

        Invitation invitation = invitationDao.findById(request.getId()).orElseThrow(
                ()-> new IllegalStateException("Invitation Not Found")
        );

        if (!invitation.getReceiver().getId().equals(receiver.getId())) {
            throw new IllegalStateException("You are not authorized to accept this invitation");
        }

        if (invitation.getState() != InvitationState.PENDING) {
            throw new IllegalStateException("Invitation is no longer pending");
        }

        if (invitation.getExpiredAt().isBefore(LocalDateTime.now())) {
            invitation.setState(InvitationState.EXPIRED);
            invitationDao.save(invitation);
            throw new IllegalStateException("Invitation has expired");
        }

        invitation.setState(InvitationState.ACCEPTED);
        invitationDao.save(invitation);

        membershipService.createMember(receiver, invitation.getProject());
    }

    @Override
    public void declineInvitation(Invitation request){
        User receiver = util.getCurrentUser();
        if (receiver == null) {
            throw new IllegalStateException("User Not Authenticated");
        }

        Invitation invitation = invitationDao.findById(request.getId()).orElseThrow(
                ()-> new IllegalStateException("Invitation Not Found")
        );

        if (!invitation.getReceiver().getId().equals(receiver.getId())) {
            throw new IllegalStateException("You are not authorized to decline this invitation");
        }

        if (invitation.getState() != InvitationState.PENDING) {
            throw new IllegalStateException("Invitation is no longer pending");
        }

        if (invitation.getExpiredAt().isBefore(LocalDateTime.now())) {
            invitation.setState(InvitationState.EXPIRED);
            invitationDao.save(invitation);
            throw new IllegalStateException("Invitation has expired");
        }

        invitation.setState(InvitationState.DECLINED);
        invitationDao.save(invitation);
    }
}

package aymh.momentum.service.user.facade;

import aymh.momentum.bean.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {
    Invitation sendInvitation(Invitation request) throws MessagingException;

    void acceptInvitation(Invitation request);

    void declineInvitation(Invitation request);
}

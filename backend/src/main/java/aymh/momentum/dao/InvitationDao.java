
package aymh.momentum.dao;

import aymh.momentum.bean.Invitation;
import aymh.momentum.bean.Project;
import aymh.momentum.security.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface InvitationDao extends JpaRepository<Invitation,Long> {

    boolean existsBySenderAndReceiverAndProjectAndExpiredAtAfter(User sender, User receiver, Project project, LocalDateTime expiredAtAfter);
}

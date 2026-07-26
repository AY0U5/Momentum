package aymh.momentum.dao;

import aymh.momentum.bean.Membership;
import aymh.momentum.bean.Project;
import aymh.momentum.security.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipDao extends JpaRepository<Membership,Long> {

    Optional<Membership> findByUserAndProjectAndActiveTrue(User user, Project project);

    @Query("SELECT m FROM Membership m JOIN FETCH m.project p " +
            "WHERE m.user = :user AND m.active = true " +
            "ORDER BY m.pinned DESC, p.createdAt DESC")
    List<Membership> findAllActiveMembershipsByUser(@Param("user") User user);

    List<Membership> findAllByProjectIdAndActiveTrue(Long projectId);

    long countByProjectIdAndActiveTrue(Long projectId);

    Optional<Membership> findByUserAndProjectIdAndActiveTrue(User currentUser, Long id);

    Optional<Membership> findByUserIdAndProjectIdAndActiveTrue(Long id, Long projectId);
}

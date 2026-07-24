package aymh.momentum.dao;

import aymh.momentum.bean.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipDao extends JpaRepository<Membership,Long> {

}

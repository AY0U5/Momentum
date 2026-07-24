package aymh.momentum.dao;

import aymh.momentum.bean.TaskUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskUserDao extends JpaRepository<TaskUser,Long> {

}

package aymh.momentum.dao;

import aymh.momentum.bean.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDao extends JpaRepository<Task,Long> {

}

package aymh.momentum.dao;

import aymh.momentum.bean.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDao extends JpaRepository<Project,Long> {

}

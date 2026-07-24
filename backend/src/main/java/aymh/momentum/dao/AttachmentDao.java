package aymh.momentum.dao;

import aymh.momentum.bean.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentDao extends JpaRepository<Attachment,Long> {

}

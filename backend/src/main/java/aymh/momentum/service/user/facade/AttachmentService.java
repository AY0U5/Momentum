package aymh.momentum.service.user.facade;

import aymh.momentum.bean.Attachment;
import aymh.momentum.bean.Task;
import aymh.momentum.security.bean.User;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

    Attachment addAttachmentToTask(Task task, User user, MultipartFile file);
}

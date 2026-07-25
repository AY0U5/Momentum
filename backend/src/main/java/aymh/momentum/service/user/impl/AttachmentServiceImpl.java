package aymh.momentum.service.user.impl;

import aymh.momentum.bean.Attachment;
import aymh.momentum.bean.Task;
import aymh.momentum.dao.AttachmentDao;
import aymh.momentum.security.bean.User;
import aymh.momentum.security.common.service.MinioService;
import aymh.momentum.service.user.facade.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentDao attachmentDao;
    private final MinioService minioService;

    @Value("${application.minio.buckets.task-attachments}")
    private String taskAttachmentsBucket;
    @Value("${application.minio.url}")
    private String minioBaseUrl;

    @Transactional
    @Override
    public Attachment addAttachmentToTask(Task task, User user, MultipartFile file) {
        String storedFileName = minioService.uploadFile(file, taskAttachmentsBucket);
        String fileUrl = minioBaseUrl + "/" + taskAttachmentsBucket + "/" + storedFileName;

        Attachment attachment = Attachment.builder()
                .fileName(file.getOriginalFilename())
                .fileUrl(fileUrl)
                .fileSize(BigDecimal.valueOf(file.getSize()))
                .uploadedAt(LocalDateTime.now())
                .uploader(user)
                .task(task)
                .build();

        return attachmentDao.save(attachment);
    }
}

package aymh.momentum.ws.converter;

import aymh.momentum.bean.Attachment;
import aymh.momentum.security.ws.converter.UserConverter;
import aymh.momentum.ws.dto.AttachmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttachmentConverter {

    private final UserConverter userConverter;
    private final TaskConverter taskConverter;

    public Attachment toBean(AttachmentDto dto){
        if (dto == null) {
            return null;
        }
        return Attachment.builder()
                .id(dto.getId())
                .fileName(dto.getFileName())
                .fileUrl(dto.getFileUrl())
                .fileSize(dto.getFileSize())
                .uploader(userConverter.toBean(dto.getUploader()))
                .uploadedAt(dto.getUploadedAt())
                .task(taskConverter.toBean(dto.getTask()))
                .build();
    }

    public AttachmentDto toDto(Attachment bean){
        if (bean == null) {
            return null;
        }
        return AttachmentDto.builder()
                .id(bean.getId())
                .fileName(bean.getFileName())
                .fileUrl(bean.getFileUrl())
                .fileSize(bean.getFileSize())
                .uploader(userConverter.toDto(bean.getUploader()))
                .uploadedAt(bean.getUploadedAt())
                .task(taskConverter.toDto(bean.getTask()))
                .build();
    }
}

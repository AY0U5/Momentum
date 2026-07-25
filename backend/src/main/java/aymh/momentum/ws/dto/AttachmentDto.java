package aymh.momentum.ws.dto;

import aymh.momentum.bean.Task;
import aymh.momentum.security.bean.User;
import aymh.momentum.security.ws.dto.UserDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AttachmentDto {

    private Long id;
    private String fileName;
    private String fileUrl;
    private BigDecimal fileSize;
    private LocalDateTime uploadedAt;
    private UserDto uploader;
    private TaskDto task;
}

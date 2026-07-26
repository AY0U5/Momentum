package aymh.momentum.ws.converter;

import aymh.momentum.bean.Invitation;
import aymh.momentum.security.ws.converter.UserConverter;
import aymh.momentum.ws.dto.InvitationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvitationConverter {

    private final UserConverter userConverter;
    private final ProjectConverter projectConverter;

    public Invitation toBean(InvitationDto dto){
        if (dto == null) {
            return null;
        }
        return Invitation.builder()
                .id(dto.getId())
                .createdAt(dto.getCreatedAt())
                .expiredAt(dto.getExpiredAt())
                .state(dto.getState())
                .sender(userConverter.toBean(dto.getSender()))
                .receiver(userConverter.toBean(dto.getReceiver()))
                .project(projectConverter.toBean(dto.getProject()))
                .build();
    }

    public InvitationDto toDto(Invitation bean){
        if (bean == null) {
            return null;
        }
        return InvitationDto.builder()
                .id(bean.getId())
                .createdAt(bean.getCreatedAt())
                .expiredAt(bean.getExpiredAt())
                .state(bean.getState())
                .sender(userConverter.toDto(bean.getSender()))
                .receiver(userConverter.toDto(bean.getReceiver()))
                .project(projectConverter.toDto(bean.getProject()))
                .build();
    }
}

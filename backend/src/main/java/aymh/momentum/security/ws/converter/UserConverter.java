package aymh.momentum.security.ws.converter;

import aymh.momentum.security.bean.Role;
import aymh.momentum.security.bean.User;
import aymh.momentum.security.ws.dto.RoleDto;
import aymh.momentum.security.ws.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final RoleConverter roleConverter;

    public User toBean(UserDto dto){
        if (dto == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(dto,user);
        Role roleBean = roleConverter.toBean(dto.getRole());
        user.setRole(roleBean);
        return user;
    }

    public UserDto toDto(User bean){
        if (bean == null) {
            return null;
        }
        UserDto user = new UserDto();
        BeanUtils.copyProperties(bean,user);
        RoleDto roleDto = roleConverter.toDto(bean.getRole());
        user.setRole(roleDto);
        return user;
    }
}

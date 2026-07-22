package aymh.momentum.security.ws.converter;

import aymh.momentum.security.bean.Role;
import aymh.momentum.security.ws.dto.RoleDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {

    public Role toBean(RoleDto dto){
        if (dto == null) {
            return null;
        }
        Role role = new Role();
        BeanUtils.copyProperties(dto,role);
        return role;
    }

    public RoleDto toDto(Role bean){
        if (bean == null) {
            return null;
        }
        RoleDto role = new RoleDto();
        BeanUtils.copyProperties(bean,role);
        return role;
    }
}

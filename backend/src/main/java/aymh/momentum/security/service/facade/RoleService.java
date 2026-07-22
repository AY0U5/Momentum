package aymh.momentum.security.service.facade;

import aymh.momentum.security.bean.Role;

import java.util.Optional;

public interface RoleService {
    Role save(Role role);

    Optional<Role> findByAuthority(String authority);
}

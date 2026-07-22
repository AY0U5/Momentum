package aymh.momentum.security.service.impl;

import aymh.momentum.security.bean.Role;
import aymh.momentum.security.dao.RoleDao;
import aymh.momentum.security.service.facade.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleDao dao;

    @Override
    public Role save(Role role) {
        return dao.save(role);
    }

    @Override
    public Optional<Role> findByAuthority(String authority) {
        return dao.findByAuthority(authority);
    }
}

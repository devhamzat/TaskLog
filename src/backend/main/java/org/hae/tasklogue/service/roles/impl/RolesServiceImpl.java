package org.hae.tasklogue.service.roles.impl;

import lombok.RequiredArgsConstructor;
import org.hae.tasklogue.entity.Role;
import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
import org.hae.tasklogue.repository.RoleRepository;
import org.hae.tasklogue.service.roles.RolesService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService {
    private final RoleRepository roleRepository;

    @Override
    public Role createRole(ApplicationUser user) {
        Role role = new Role();
        role.setName(createUserRole(user.getUsername()));
        role.setCreatedDate(user.getCreatedDate());
        role.setApplicationUsers(user);
        role.setLastModifiedDate(user.getLastModifiedDate());
        roleRepository.save(role);
        return role;
    }

    @Override
    public String createUserRole(String name) {
        return "USER" + name;
    }
}

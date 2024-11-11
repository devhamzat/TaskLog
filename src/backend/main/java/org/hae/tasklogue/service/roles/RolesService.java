package org.hae.tasklogue.service.roles;

import org.hae.tasklogue.entity.Role;
import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
import org.springframework.stereotype.Service;

@Service
public interface RolesService {
   default Role createRole(ApplicationUser user){
       return null;
   };
   default String createUserRole (String user){
       return null;
   }

}
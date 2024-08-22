package org.hae.tasklogue.repository;

import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, String> {
    Optional<ApplicationUser> findApplicationUserByUserName(String userName);
}

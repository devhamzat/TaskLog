package org.hae.tasklogue.repository;

import org.hae.tasklogue.entity.applicationUser.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);
}

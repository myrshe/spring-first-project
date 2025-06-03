package org.jetbrains.semwork_2sem.repository;

import org.jetbrains.semwork_2sem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

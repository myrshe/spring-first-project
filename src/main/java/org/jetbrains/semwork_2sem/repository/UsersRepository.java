package org.jetbrains.semwork_2sem.repository;

import org.jetbrains.semwork_2sem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "select * from account order by count_followers desc limit 3", nativeQuery = true)
    List<User> findTop3User();

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}

package org.jetbrains.semwork_2sem.repository;

import org.jetbrains.semwork_2sem.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}

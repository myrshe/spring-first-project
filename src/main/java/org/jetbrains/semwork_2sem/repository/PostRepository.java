package org.jetbrains.semwork_2sem.repository;

import org.jetbrains.semwork_2sem.models.Post;
import org.jetbrains.semwork_2sem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAll();

    List<Post> findByUserId(Long userId);

    boolean existsByPostIdAndLikesContaining(Long postId, User user);

    List<Post> findAllByUserInOrderByDateDesc(List<User> users);

}

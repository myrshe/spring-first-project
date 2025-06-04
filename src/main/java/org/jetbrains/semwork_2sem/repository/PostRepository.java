package org.jetbrains.semwork_2sem.repository;

import org.jetbrains.semwork_2sem.models.Post;
import org.jetbrains.semwork_2sem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select * from post order by date desc", nativeQuery = true)
    List<Post> findAll();

    @Query(value = "select * from post where user_id = :userId order by date desc", nativeQuery = true)
    List<Post> findByUserId(Long userId);

    boolean existsByPostIdAndLikesContaining(Long postId, User user);

    List<Post> findAllByUserInOrderByDateDesc(List<User> users);

    @Query("select p from Post p join p.tags t where t.name = :tagName order by size(p.likes) desc")
    List<Post> findTopPostsByTagName(@Param("tagName") String tagName, Pageable pageable);

}

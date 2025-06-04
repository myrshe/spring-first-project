package org.jetbrains.semwork_2sem.repository;

import org.jetbrains.semwork_2sem.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost_PostId(Long postId);
    void deleteById(Long commentId);
}

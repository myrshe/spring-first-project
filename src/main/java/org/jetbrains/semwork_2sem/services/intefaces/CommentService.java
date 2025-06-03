package org.jetbrains.semwork_2sem.services.intefaces;

import org.jetbrains.semwork_2sem.dto.CommentDto;
import org.jetbrains.semwork_2sem.dto.CommentForm;

import java.util.List;

public interface CommentService {
    List<CommentDto> findAllByPostId(Long postId);
    public void addComment(CommentForm commentForm, Long currentUserId, Long postId);
}

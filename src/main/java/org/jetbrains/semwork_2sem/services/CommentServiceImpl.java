package org.jetbrains.semwork_2sem.services;

import org.jetbrains.semwork_2sem.dto.CommentDto;
import org.jetbrains.semwork_2sem.dto.CommentForm;
import org.jetbrains.semwork_2sem.models.Comment;
import org.jetbrains.semwork_2sem.repository.CommentRepository;
import org.jetbrains.semwork_2sem.repository.PostRepository;
import org.jetbrains.semwork_2sem.repository.UsersRepository;
import org.jetbrains.semwork_2sem.services.intefaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<CommentDto> findAllByPostId(Long postId) {
        List<Comment> comments = commentRepository.findAllByPost_PostId(postId);
        return CommentDto.from(comments);
    }

    @Override
    public void addComment(CommentForm commentForm, Long currentUserId, Long postId) {
        Comment comment = Comment.builder()
                .text(commentForm.getText())
                .post(postRepository.findById(postId).get())
                .user(usersRepository.findById(currentUserId).get())
                .build();
        commentRepository.save(comment);
    }
}

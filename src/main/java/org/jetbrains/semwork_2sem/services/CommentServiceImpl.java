package org.jetbrains.semwork_2sem.services;

import org.jetbrains.semwork_2sem.dto.CommentDto;
import org.jetbrains.semwork_2sem.dto.CommentForm;
import org.jetbrains.semwork_2sem.models.Comment;
import org.jetbrains.semwork_2sem.models.Post;
import org.jetbrains.semwork_2sem.models.User;
import org.jetbrains.semwork_2sem.repository.CommentRepository;
import org.jetbrains.semwork_2sem.repository.PostRepository;
import org.jetbrains.semwork_2sem.repository.UsersRepository;
import org.jetbrains.semwork_2sem.services.intefaces.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger userLogger = LoggerFactory.getLogger("user");
    private static final Logger commentLogger = LoggerFactory.getLogger("comment");


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<CommentDto> findAllByPostId(Long postId) {
        try {
            List<Comment> comments = commentRepository.findAllByPost_PostId(postId);
            return CommentDto.from(comments);
        } catch (Exception e) {
            commentLogger.error("ошибка при получении комментариев поста с postId {}", postId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ошибка при получении комментариев поста", e);
        }
    }

    @Override
    public void addComment(CommentForm commentForm, Long currentUserId, Long postId) {
        try {
            Optional<Post> postOptional = postRepository.findById(postId);
            Optional<User> userOptional = usersRepository.findById(currentUserId);
            if (postOptional.isEmpty()) {
                commentLogger.error("ошибка при добавлении комментария пост не найден с postId {}", postId);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"пост не найден");
            }
            if (userOptional.isEmpty()) {
                userLogger.error("ошибка при добавлении комментария пользователь не найдет с userId {}", currentUserId);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "пользователь не найден");
            }
            Comment comment = Comment.builder()
                    .text(commentForm.getText())
                    .post(postOptional.get())
                    .user(userOptional.get())
                    .build();
            commentRepository.save(comment);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            commentLogger.error("ошибка при добавлении комментария к посту с postId {}", postId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ошибка при добавлении комментария", e);
        }
    }

    @Override
    public List<CommentDto> getAllComments() {
        try {
            List<Comment> comments = commentRepository.findAll();
            return CommentDto.from(comments);
        } catch (Exception e) {
            commentLogger.error("ошибка при получении всех комментариев", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ошибка при получении всех комментариев", e);
        }
    }

    @Override
    public void remove(Long commentId) {
        try {
            if (!commentRepository.existsById(commentId)) {
                commentLogger.error("попытка удалить несуществующий комментарий с commentId {}", commentId);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "комментарий не найден");
            }
            commentRepository.deleteById(commentId);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            commentLogger.error("ошибка при удалении комментария с id {}", commentId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ошибка при удалении комментария", e);
        }
    }
}

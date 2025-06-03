package org.jetbrains.semwork_2sem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.semwork_2sem.models.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String text;
    private Long userId;
    private Long postId;
    private LocalDateTime date;
    private String authorUsername;

    public static CommentDto from(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .userId(comment.getUser().getId())
                .postId(comment.getPost().getPostId())
                .date(comment.getDate())
                .authorUsername(comment.getUser().getUsername())
                .build();
    }

    public static List<CommentDto> from (List<Comment> comments) {
        return comments.stream()
                .map(CommentDto::from)
                .collect(Collectors.toList());
    }
}

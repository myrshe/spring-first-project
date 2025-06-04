package org.jetbrains.semwork_2sem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.semwork_2sem.models.FileInfo;
import org.jetbrains.semwork_2sem.models.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private LocalDateTime date;
    private String text;
    private Long userId;
    private String username;
    private Integer likesCount;
    private boolean likedOrNo;
    private List<FileInfo> files;


    public static PostDto from(Post post, Long currentUserId) {
        int likesCount = post.getLikes() != null ? post.getLikes().size() : 0;
        boolean isLiked = post.isLikedByUser(currentUserId);
        return PostDto.builder()
                .id(post.getPostId())
                .date(post.getDate())
                .text(post.getText())
                .userId(post.getUser().getId())
                .username(post.getUser().getUsername())
                .likesCount(likesCount)
                .likedOrNo(isLiked)
                .files(post.getFiles())
                .build();
    }

    public static List<PostDto> from(List<Post> posts, Long currentUserId) {
        List<PostDto> result = new ArrayList<>();
        for (Post post : posts) {
            result.add(PostDto.from(post, currentUserId));
        }
        return result;
    }

}

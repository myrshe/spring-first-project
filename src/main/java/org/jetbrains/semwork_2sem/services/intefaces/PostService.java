package org.jetbrains.semwork_2sem.services.intefaces;

import org.jetbrains.semwork_2sem.dto.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts(Long currentUserId);
    List<PostDto> getAllPostsByUserId(Long userId, Long currentUserId);
    void like(Long userId, Long postId);
    PostDto getByPostId(Long postId, Long currentUserId);
    List<PostDto> getAllBySubscription(Long currentId);
    void createPost(Long userId, String text, List<MultipartFile> files, String tags);
    List<PostDto> getAllByTag(Long currentUserId, String tag, int limit);
}

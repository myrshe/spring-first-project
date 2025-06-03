package org.jetbrains.semwork_2sem.services.intefaces;

import org.jetbrains.semwork_2sem.dto.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts(Long currentUserId);
    List<PostDto> getAllPostsByUserId(Long userId, Long currentUserId);
    void like(Long userId, Long postId);
    PostDto getByPostId(Long postId, Long currentUserId);
    List<PostDto> getAllBySubscription(Long currentId);
}

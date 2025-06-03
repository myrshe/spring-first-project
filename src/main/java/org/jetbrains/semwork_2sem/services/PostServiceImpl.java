package org.jetbrains.semwork_2sem.services;

import org.jetbrains.semwork_2sem.dto.PostDto;
import org.jetbrains.semwork_2sem.models.Post;
import org.jetbrains.semwork_2sem.models.User;
import org.jetbrains.semwork_2sem.repository.PostRepository;
import org.jetbrains.semwork_2sem.repository.UsersRepository;
import org.jetbrains.semwork_2sem.services.intefaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsersRepository usersRepository;
    @Override
    public List<PostDto> getAllPosts(Long currentUserId) {
        List<Post> allPosts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
        return PostDto.from(allPosts, currentUserId);
    }

    @Override
    public List<PostDto> getAllPostsByUserId(Long userId, Long currentUserId) {
        List<Post> allPostsOfUser = postRepository.findByUserId(userId);
        return PostDto.from(allPostsOfUser, currentUserId);
    }

    @Override
    public void like(Long userId, Long postId) {
        Optional<User> user = usersRepository.findById(userId);
        Optional<Post> post = postRepository.findById(postId);
        if (postRepository.existsByPostIdAndLikesContaining(postId, user.get())) {
            post.get().getLikes().remove(user.get());
        } else {
            post.get().getLikes().add(user.get());
        }
        postRepository.save(post.get());
    }

    @Override
    public PostDto getByPostId(Long postId, Long currentUserId) {
        Optional<Post> post = postRepository.findById(postId);
        return PostDto.from(post.get(), currentUserId);
    }

    @Override
    public List<PostDto> getAllBySubscription(Long currentId) {
        Optional<User> user = usersRepository.findById(currentId);

        List<User> following = user.get().getFollowing();

        if (following == null || following.isEmpty()) {
            return List.of();
        }
        List<Post> posts = postRepository.findAllByUserInOrderByDateDesc(following);

        return PostDto.from(posts, currentId);
    }
}

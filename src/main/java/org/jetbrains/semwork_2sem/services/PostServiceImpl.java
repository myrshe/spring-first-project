package org.jetbrains.semwork_2sem.services;

import jakarta.transaction.Transactional;
import org.jetbrains.semwork_2sem.dto.PostDto;
import org.jetbrains.semwork_2sem.models.FileInfo;
import org.jetbrains.semwork_2sem.models.Post;
import org.jetbrains.semwork_2sem.models.User;
import org.jetbrains.semwork_2sem.repository.FileInfoRepository;
import org.jetbrains.semwork_2sem.repository.PostRepository;
import org.jetbrains.semwork_2sem.repository.UsersRepository;
import org.jetbrains.semwork_2sem.services.intefaces.FileStorageService;
import org.jetbrains.semwork_2sem.services.intefaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private FileStorageService fileStorageService;

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
        User user = usersRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("Такого пользователя не существует"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Такого поста не существует"));
        if (postRepository.existsByPostIdAndLikesContaining(postId, user)) {
            post.getLikes().remove(user);
        } else {
            post.getLikes().add(user);
        }
        postRepository.save(post);
    }

    @Override
    public PostDto getByPostId(Long postId, Long currentUserId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Такого поста не существует"));
        return PostDto.from(post, currentUserId);
    }

    @Override
    public List<PostDto> getAllBySubscription(Long currentId) {
        User user = usersRepository.findById(currentId).orElseThrow(()-> new IllegalArgumentException("Такого пользователя не существует"));

        List<User> following = user.getFollowing();

        if (following == null || following.isEmpty()) {
            return List.of();
        }
        List<Post> posts = postRepository.findAllByUserInOrderByDateDesc(following);

        return PostDto.from(posts, currentId);
    }


    @Override
    @Transactional
    public void createPost(Long userId, String text, List<MultipartFile> files) {
        if (files != null && files.size() > 10) {
            throw new IllegalArgumentException("Нельзя прикрепить больше 10 файлов");
        }

        User user = usersRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Такого пользователя не существует"));

        Post post = Post.builder()
                .text(text)
                .user(user)
                .likes(new ArrayList<>())
                .comments(new ArrayList<>())
                .date(LocalDateTime.now())
                .files(new ArrayList<>())
                .build();

        postRepository.save(post);

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                FileInfo saved = fileStorageService.saveFile(file);
                saved.getPosts().add(post);
                post.getFiles().add(saved);
                fileInfoRepository.save(saved);
            }
            postRepository.save(post);
        }
    }


}

package org.jetbrains.semwork_2sem.services;

import jakarta.transaction.Transactional;
import org.jetbrains.semwork_2sem.converters.TagToTagDtoConverter;
import org.jetbrains.semwork_2sem.dto.PostDto;
import org.jetbrains.semwork_2sem.dto.TagDto;
import org.jetbrains.semwork_2sem.models.FileInfo;
import org.jetbrains.semwork_2sem.models.Post;
import org.jetbrains.semwork_2sem.models.Tag;
import org.jetbrains.semwork_2sem.models.User;
import org.jetbrains.semwork_2sem.repository.FileInfoRepository;
import org.jetbrains.semwork_2sem.repository.PostRepository;
import org.jetbrains.semwork_2sem.repository.TagRepository;
import org.jetbrains.semwork_2sem.repository.UsersRepository;
import org.jetbrains.semwork_2sem.services.intefaces.FileStorageService;
import org.jetbrains.semwork_2sem.services.intefaces.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {
    private static final Logger postLogger = LoggerFactory.getLogger("post");

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagToTagDtoConverter tagToDtoConverter;

    @Override
    public List<PostDto> getAllPosts(Long currentUserId) {
        try {
            List<Post> allPosts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
            return PostDto.from(allPosts, currentUserId);
        } catch (Exception e) {
            postLogger.error("произошла ошибка при получении всех постов", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "произошла ошибка при получении всех постов");
        }
    }

    @Override
    public List<PostDto> getAllPostsByUserId(Long userId, Long currentUserId) {
        try {
            List<Post> allPostsOfUser = postRepository.findByUserId(userId);
            return PostDto.from(allPostsOfUser, currentUserId);
        } catch (Exception e) {
            postLogger.error("произошла ошибка при получении постов пользователя с id {}", userId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "произошла ошибка при получении постов пользователя", e);
        }
    }

    @Override
    public void like(Long userId, Long postId) {
        try {
            User user = usersRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "пользователь не найден"));
            Post post = postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "пост не найден"));
            if (postRepository.existsByPostIdAndLikesContaining(postId, user)) {
                post.getLikes().remove(user);
            } else {
                post.getLikes().add(user);
            }
            postRepository.save(post);
        } catch (ResponseStatusException e) {
            postLogger.error("произошла ошибка при попытке поставить лайк пользователем с id {}", userId, e);
            throw e;
        } catch (Exception e) {
            postLogger.error("произошла ошибка при попытке поставить лайк ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ошибка при попытке поставить лайк", e);
        }
    }

    @Override
    public PostDto getByPostId(Long postId, Long currentUserId) {
        try {
            Post post = postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "пост не найден"));
            return PostDto.from(post, currentUserId);
        } catch (ResponseStatusException e) {
            postLogger.warn("пост с id {} не найден", postId);
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ошибка при получении поста", e);
        }
    }

    @Override
    public List<PostDto> getAllBySubscription(Long currentId) {
        try {
            User user = usersRepository.findById(currentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "пользователь не найден"));
            List<User> following = user.getFollowing();

            if (following == null || following.isEmpty()) {
                return List.of();
            }
            List<Post> posts = postRepository.findAllByUserInOrderByDateDesc(following);
            return PostDto.from(posts, currentId);
        } catch (ResponseStatusException e) {
            postLogger.error("пользователь с id {} не найден при попытке загрузить подписки", currentId);
            throw e;
        } catch (Exception e) {
            postLogger.error("ошибка при получении постов подписок пользователя с id {}", currentId,e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ошибка при получении постов подписок", e);
        }
    }


    @Override
    @Transactional
    public void createPost(Long userId, String text, List<MultipartFile> files, String tags) {
        try {
            if (files != null && files.size() > 10) {
                postLogger.warn("пользователь с id {} попытался загрузить {} файлов", userId, files.size());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "нельзя прикрепить больше 10 файлов");
            }
            User user = usersRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "пользователь не найден"));
            Post post = Post.builder()
                    .text(text)
                    .user(user)
                    .likes(new ArrayList<>())
                    .comments(new ArrayList<>())
                    .date(LocalDateTime.now())
                    .files(new ArrayList<>())
                    .tags(new ArrayList<>())
                    .build();
            postRepository.save(post);
            if (tags != null && !tags.isBlank()) {
                String[] tagNames = tags.toLowerCase().split("\\s+");
                for (String tagNameEntity : tagNames) {
                    String tagName = tagNameEntity.startsWith("#") ? tagNameEntity.substring(1) : tagNameEntity;
                    Tag tag = tagRepository.findByName(tagName)
                            .orElseGet(() -> tagRepository.save(Tag.builder().name(tagName).build()));
                    post.getTags().add(tag);
                }
            }
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
        } catch (ResponseStatusException e) {
            postLogger.error("пользователь с id {} не найден при попытке создать пост", userId);
            throw e;
        } catch (Exception e) {
            postLogger.error("ошибка при создании поста юзером с id {} {}", userId, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ошибка при создании поста", e);
        }
    }

    @Override
    public List<PostDto> getAllByTag(Long currentUserId, String tag, int limit) {
        try {
            List<Post> posts = postRepository.findTopPostsByTagName(tag, PageRequest.of(0, limit));
            return PostDto.from(posts, currentUserId);
        } catch (Exception e) {
            postLogger.error("ошибка при получении постов по тегу {}", tag, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ошибка при получении постов по тегу", e);
        }
    }


    public List<TagDto> convertTagsToDtos(List<Tag> tags) {
        try {
            return tags.stream().map(tagToDtoConverter::convert).collect(Collectors.toList());
        } catch (Exception e) {
            postLogger.error("ошибка при конвертации тега(ов): {}", tags, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ошибка при попытке конвертации тега");
        }
    }

}

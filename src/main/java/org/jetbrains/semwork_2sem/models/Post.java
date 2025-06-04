package org.jetbrains.semwork_2sem.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user", "likes", "comments", "files"})
@EqualsAndHashCode(exclude = {"user", "likes", "comments", "files"})

@Entity

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String text;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(name= "posts_likes",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "postId"),
            inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"))
    private List<User> likes;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToMany(mappedBy = "posts")
    private List<FileInfo> files = new ArrayList<>();

    public boolean isLikedByUser(Long userId) {//проверка лайкнул ли пользователь пост (для отображения на фронте)
        if (likes == null || likes.isEmpty()) {
            return false;
        }
        return likes.stream().anyMatch(user -> user.getId().equals(userId));
    }
}

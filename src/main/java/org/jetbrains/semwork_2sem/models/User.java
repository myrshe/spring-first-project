package org.jetbrains.semwork_2sem.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data


@Entity
@Table(name = "account")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;


    private int subscribers_count;//подписчики

    private int subscriptions_count;//подписки



    @OneToMany(mappedBy = "user")
    private List<Post> createdPosts;


    @ManyToMany(mappedBy = "likes")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "target_user_id")
    )
    private List<User> following; // На кого подписан

    @ManyToMany(mappedBy = "following")
    private List<User> followers; // Кто подписан на меня

    public void follow(User user) {
        following.add(user);
        user.getFollowers().add(this);
    }

    public void unfollow(User user) {
        following.remove(user);
        user.getFollowers().remove(this);
    }

    public List<User> getFollowers() {
        return followers;
    }
}

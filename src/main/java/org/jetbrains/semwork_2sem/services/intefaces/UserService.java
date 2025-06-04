package org.jetbrains.semwork_2sem.services.intefaces;

import org.jetbrains.semwork_2sem.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findUserById(Long userId);
    void subscribe(Long userId, Long targetUserId);
    boolean checkFollow(Long subscriberId, Long targetId);
    List<UserDto> getFollowings(Long userId); // на кого подписан
    List<UserDto> getFollowers(Long userId); // подписчики
    int countFollowing(Long userId);//подписки
    int countFollowers(Long userId);//подписчики
    List<UserDto> getTopAuthors();
}

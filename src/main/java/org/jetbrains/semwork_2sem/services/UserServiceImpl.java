package org.jetbrains.semwork_2sem.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jetbrains.semwork_2sem.dto.UserDto;
import org.jetbrains.semwork_2sem.models.User;
import org.jetbrains.semwork_2sem.repository.UsersRepository;
import org.jetbrains.semwork_2sem.services.intefaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDto findUserById(Long userId) {
        return UserDto.from(usersRepository.findById(userId).get());
    }

    @Transactional
    @Override
    public void subscribe(Long userId, Long targetUserId) {
        if (userId.equals(targetUserId)) {
            throw new IllegalArgumentException("Нельзя подписаться/отписаться на самого себя");
        }

        User subscriber = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        User target = usersRepository.findById(targetUserId).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        if (subscriber.getFollowing().contains(target)) {//отписка
            subscriber.getFollowing().remove(target);
            target.getFollowers().remove(subscriber);
            subscriber.setCountFollowing(Math.max(subscriber.getCountFollowing() - 1, 0));
            target.setCountFollowers(Math.max(target.getCountFollowers() - 1, 0));
        } else {//подписка
            subscriber.getFollowing().add(target);
            target.getFollowers().add(subscriber);
            subscriber.setCountFollowing(subscriber.getCountFollowing() + 1);
            target.setCountFollowers(target.getCountFollowers() + 1);
        }
        usersRepository.save(subscriber);
        usersRepository.save(target);
    }

    @Override
    public boolean checkFollow(Long userId, Long targetId) {
        User subscriber = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        User target = usersRepository.findById(targetId).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        return (subscriber.getFollowing().contains(target));
    }

    @Override
    public List<UserDto> getFollowings(Long userId) {
        User user = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        List<User> followings = user.getFollowing();
        return UserDto.from(followings);
    }

    @Override
    public List<UserDto> getFollowers(Long userId) {
        User user = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        List<User> followers = user.getFollowers();
        return UserDto.from(followers);
    }

    @Override
    public int countFollowing(Long userId) {
        User user = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        return user.getCountFollowing();
    }

    @Override
    public int countFollowers(Long userId) {
        User user = usersRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        return user.getCountFollowers();
    }

    @Override
    public List<UserDto> getTopAuthors() {
        List<User> top3Users = usersRepository.findTop3User();
        return UserDto.from(top3Users);
    }


}

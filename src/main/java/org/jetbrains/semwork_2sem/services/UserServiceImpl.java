package org.jetbrains.semwork_2sem.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.jetbrains.semwork_2sem.dto.UserDto;
import org.jetbrains.semwork_2sem.models.User;
import org.jetbrains.semwork_2sem.repository.UsersRepository;
import org.jetbrains.semwork_2sem.services.intefaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger userLogger = LoggerFactory.getLogger("user");

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDto findUserById(Long userId) {
        try {
            User user = usersRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "пользователь не найден"));
            return UserDto.from(user);
        } catch (ResponseStatusException e) {
            userLogger.error("пользователь с id {} не найден", userId);
            throw e;
        } catch (Exception e) {
            userLogger.error("ошибка при получении пользователя с id {}", userId);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ошибка при получении пользователя", e);
        }
    }

    @Transactional
    @Override
    public void subscribe(Long userId, Long targetUserId) {
        try {
            if (userId.equals(targetUserId)) {
                userLogger.error("пользователь с id {} пытался отписаться/подписаться на самого себя", userId);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "нельзя подписаться/отписаться на самого себя");
            }
            User subscriber = usersRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "пользователь не найден"));
            userLogger.error("при попытке подписаться/отписаться у юзера с id {} возникла ошибка", userId);
            User target = usersRepository.findById(targetUserId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "пользователь не найден"));
            userLogger.error("при попытке подписаться/отписаться у юзера с id {} возникла ошибка", targetUserId);

            if (subscriber.getFollowing().contains(target)) {// Отписка
                subscriber.getFollowing().remove(target);
                target.getFollowers().remove(subscriber);
                subscriber.setCountFollowing(Math.max(subscriber.getCountFollowing() - 1, 0));
                target.setCountFollowers(Math.max(target.getCountFollowers() - 1, 0));
            } else {// Подписка
                subscriber.getFollowing().add(target);
                target.getFollowers().add(subscriber);
                subscriber.setCountFollowing(subscriber.getCountFollowing() + 1);
                target.setCountFollowers(target.getCountFollowers() + 1);
            }
            usersRepository.save(subscriber);
            usersRepository.save(target);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            userLogger.error("ошибка при обработке подписки у юзера с id {}", userId);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ошибка при обработке подписки", e);
        }
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
        try {
            List<User> top3Users = usersRepository.findTop3User();
            return UserDto.from(top3Users);
        } catch (Exception e) {
            userLogger.error("ошибка при получении топ авторов");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ошибка при получении топ авторов", e);
        }
    }


}

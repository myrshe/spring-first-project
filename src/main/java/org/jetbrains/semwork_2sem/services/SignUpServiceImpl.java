package org.jetbrains.semwork_2sem.services;

import org.jetbrains.semwork_2sem.dto.UserForm;
import org.jetbrains.semwork_2sem.models.Role;
import org.jetbrains.semwork_2sem.models.User;
import org.jetbrains.semwork_2sem.repository.UsersRepository;
import org.jetbrains.semwork_2sem.services.intefaces.SignUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
public class SignUpServiceImpl implements SignUpService {
    private static final Logger userLogger = LoggerFactory.getLogger("user");

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void addUser(UserForm userForm){
        try {
            if (usersRepository.existsByEmail(userForm.getEmail())) {
                userLogger.error("попытка регистрации с уже существующим email {}", userForm.getEmail());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "пользователь с таким email уже существует");
            }
            if (usersRepository.existsByUsername(userForm.getUsername())) {
                userLogger.error("попытка регистрации с уже существующим никнеймом {}", userForm.getUsername());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "пользователь с таким именем уже существует");
            }
            if (userForm.getEmail() == null || userForm.getEmail().isBlank()) {
                userLogger.error("пустой email при регистрации");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email не может быть пустым");
            }
            if (userForm.getPassword() == null || userForm.getPassword().isBlank()) {
                userLogger.error("пустой пароль при регистрации");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "пароль не может быть пустым");
            }
            if (userForm.getUsername() == null || userForm.getUsername().isBlank()) {
                userLogger.error("пустой username при регистрации");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "имя пользователя не может быть пустым");
            }
            User user = User.builder()
                    .email(userForm.getEmail())
                    .password(passwordEncoder.encode(userForm.getPassword()))
                    .username(userForm.getUsername())
                    .role(Role.USER)
                    .countFollowers(0)
                    .countFollowing(0)
                    .following(new ArrayList<>())
                    .followers(new ArrayList<>())
                    .build();
            usersRepository.save(user);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            userLogger.error("ошибка при регистрации {}", userForm, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "произошла ошибка при регистрации", e);
        }
    }
}

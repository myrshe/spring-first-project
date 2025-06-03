package org.jetbrains.semwork_2sem.services;

import freemarker.template.TemplateException;
import org.jetbrains.semwork_2sem.dto.UserForm;
import org.jetbrains.semwork_2sem.models.Role;
import org.jetbrains.semwork_2sem.models.User;
import org.jetbrains.semwork_2sem.repository.UsersRepository;
import org.jetbrains.semwork_2sem.services.intefaces.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SignUpServiceImpl implements SignUpService {


    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void addUser(UserForm userForm) throws TemplateException, IOException {
        User user = User.builder()
                .email(userForm.getEmail())
                .password(passwordEncoder.encode(userForm.getPassword()))
                .username(userForm.getUsername())
                .role(Role.USER)
                .countFollowers(0)
                .countFollowing(0)
                .build();
        usersRepository.save(user);
    }
}

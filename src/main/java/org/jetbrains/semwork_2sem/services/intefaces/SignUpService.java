package org.jetbrains.semwork_2sem.services.intefaces;

import freemarker.template.TemplateException;
import org.jetbrains.semwork_2sem.dto.UserForm;

import java.io.IOException;

public interface SignUpService {
    public void addUser(UserForm userForm) throws TemplateException, IOException;
}

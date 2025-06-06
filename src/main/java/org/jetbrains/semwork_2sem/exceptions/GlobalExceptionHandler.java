package org.jetbrains.semwork_2sem.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResponseStatusException.class)
    public String handleStatusException(ResponseStatusException exception, Model model) {
        log.error("Ошибка обработки запроса: {}", exception.getReason());
        model.addAttribute("status", exception.getStatusCode().value());
        model.addAttribute("errorMessage", exception.getMessage());
        model.addAttribute("error", exception.getReason());
        return "error";
    }
}

package org.jetbrains.semwork_2sem.controllers;
import org.jetbrains.semwork_2sem.security.details.MyUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.jetbrains.semwork_2sem.dto.JokeResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.client.RestTemplate;

@Controller
public class JokeController {

    @GetMapping("/randomJoke")
    public String showJokePage(Model model,
                               @AuthenticationPrincipal MyUserDetails currentUser) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://v2.jokeapi.dev/joke/Any?type=single";
        JokeResponseDto jokeResponse = restTemplate.getForObject(url, JokeResponseDto.class);

        model.addAttribute("currentId", currentUser.getUser().getId());
        model.addAttribute("joke", jokeResponse.getJoke());
        model.addAttribute("category", jokeResponse.getCategory());

        return "joke_page";
    }
}
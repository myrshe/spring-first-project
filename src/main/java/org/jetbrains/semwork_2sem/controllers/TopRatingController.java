package org.jetbrains.semwork_2sem.controllers;


import org.jetbrains.semwork_2sem.dto.PostDto;
import org.jetbrains.semwork_2sem.dto.UserDto;
import org.jetbrains.semwork_2sem.security.details.MyUserDetails;
import org.jetbrains.semwork_2sem.services.intefaces.PostService;
import org.jetbrains.semwork_2sem.services.intefaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TopRatingController {
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping("/topAuthors")
    public String getTopAuthorsPage(@AuthenticationPrincipal MyUserDetails currentUser,
                                    Model model) {
        Long currentUserId = currentUser.getUser().getId();
        List<UserDto> top3Authors = userService.getTopAuthors();
        model.addAttribute("authors", top3Authors);
        model.addAttribute("currentId",currentUserId);
        return "top_authors_page";
    }

    @GetMapping("/tag/{tag}")
    public String getTopTagsPage(@PathVariable("tag") String tag,
                                 @AuthenticationPrincipal MyUserDetails currentUser,
                                 Model model) {
        Long currentUserId = currentUser.getUser().getId();
        List<PostDto> postByTag = postService.getAllByTag(currentUserId, tag, 10);
        model.addAttribute("currentId", currentUserId);
        model.addAttribute("posts", postByTag);
        return "top_tag_page";
    }
}

package org.jetbrains.semwork_2sem.controllers;

import org.jetbrains.semwork_2sem.dto.PostDto;
import org.springframework.ui.Model;
import org.jetbrains.semwork_2sem.security.details.MyUserDetails;
import org.jetbrains.semwork_2sem.services.intefaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FeedController {
    @Autowired
    private PostService postService;

    @GetMapping("/globalFeed")
    public String getGlobalFeedPage(@AuthenticationPrincipal MyUserDetails currentUser,
                                    Model model) {
        List<PostDto> allPosts = postService.getAllPosts(currentUser.getUser().getId());

        model.addAttribute("allPosts", allPosts);
        model.addAttribute("currentId", currentUser.getUser().getId());

        return "feed_global_page";
    }

    @GetMapping("/subscribeFeed")
    public String getSubscribeFeedPage(@AuthenticationPrincipal MyUserDetails currentUser,
                                       Model model) {

        List<PostDto> allPostsBySubscription = postService.getAllBySubscription(currentUser.getUser().getId());

        model.addAttribute("posts", allPostsBySubscription);
        model.addAttribute("currentId", currentUser.getUser().getId());
        return "feed_subscribe_page";
    }
}

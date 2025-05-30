package org.jetbrains.semwork_2sem.controllers;

import org.jetbrains.semwork_2sem.dto.PostDto;
import org.jetbrains.semwork_2sem.dto.UserDto;
import org.jetbrains.semwork_2sem.models.Role;
import org.jetbrains.semwork_2sem.security.details.MyUserDetails;
import org.jetbrains.semwork_2sem.services.intefaces.PostService;
import org.jetbrains.semwork_2sem.services.intefaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping("/profile/{user-id}")
    public String getProfilePage(@PathVariable("user-id") Long profileId,
                                 Model model,
                                 @AuthenticationPrincipal MyUserDetails currentUser) {
        Long currentUserId = currentUser.getUser().getId();
        boolean isOwner = profileId.equals(currentUserId);
        UserDto userProfile = userService.findUserById(profileId);
        if (isOwner) {

            model.addAttribute("email", userProfile.getEmail());

        } else {
            model.addAttribute("");//тут проверка на подписку
        }
        List<PostDto> allProfilePosts = postService.getAllPostsByUserId(profileId, currentUserId);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(allProfilePosts.toString());
        boolean isAdmin = userProfile.getRole() == Role.ADMIN;

        model.addAttribute("userId", profileId);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("posts", allProfilePosts);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("username", userProfile.getUsername());
        return "profile_page";
    }
}

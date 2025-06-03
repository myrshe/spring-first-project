package org.jetbrains.semwork_2sem.controllers;

import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping("/profile/{userId}")
    public String getProfilePage(@PathVariable("userId") Long profileId,
                                 Model model,
                                 @AuthenticationPrincipal MyUserDetails currentUser) {
        Long currentUserId = currentUser.getUser().getId();
        boolean isOwner = profileId.equals(currentUserId);
        UserDto userProfile = userService.findUserById(profileId);
        if (isOwner) {

            model.addAttribute("email", userProfile.getEmail());

        } else {

            boolean checkFollowing = userService.checkFollow(currentUserId, profileId);
            model.addAttribute("checkFollowing", checkFollowing);//тут проверка на подписку
        }
        List<PostDto> allProfilePosts = postService.getAllPostsByUserId(profileId, currentUserId);

        int countFollowers = userService.countFollowers(profileId);
        int countFollowing = userService.countFollowing(profileId);

        boolean isAdmin = userProfile.getRole() == Role.ADMIN;


        model.addAttribute("countFollowers", countFollowers);
        model.addAttribute("countFollowing", countFollowing);
        model.addAttribute("currentId", currentUserId);
        model.addAttribute("userId", profileId);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("posts", allProfilePosts);
        model.addAttribute("role", isAdmin);
        model.addAttribute("username", userProfile.getUsername());
        return "profile_page";
    }


    @PostMapping("/profile/{userId}/follow")
    public String follow(@PathVariable("userId") Long profileId,
                         @AuthenticationPrincipal MyUserDetails currentUser) {
        Long currentUserId = currentUser.getUser().getId();
        userService.subscribe(currentUserId,profileId);


        return "redirect:/profile/" +profileId;
    }
}

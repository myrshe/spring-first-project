package org.jetbrains.semwork_2sem.controllers;

import org.jetbrains.semwork_2sem.models.Role;
import org.jetbrains.semwork_2sem.security.details.MyUserDetails;
import org.jetbrains.semwork_2sem.services.intefaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/allComments")
    public String getAllCommentsPage(Model model,
                                     @AuthenticationPrincipal MyUserDetails currentUser) {
        boolean isAdmin = currentUser.getUser().getRole() == Role.ADMIN;
        model.addAttribute("comments", commentService.getAllComments());
        model.addAttribute("currentId",currentUser.getUser().getId());
        model.addAttribute("role", isAdmin);
        return "all_comments_page";
    }

    @PostMapping("/allComments")
    public String deleteComment(@RequestParam Long commentId) {
        commentService.remove(commentId);
        return "redirect:/allComments";
    }
}

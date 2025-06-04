package org.jetbrains.semwork_2sem.controllers;


import org.jetbrains.semwork_2sem.dto.CommentDto;
import org.jetbrains.semwork_2sem.dto.CommentForm;
import org.jetbrains.semwork_2sem.dto.PostDto;
import org.jetbrains.semwork_2sem.dto.PostForm;
import org.jetbrains.semwork_2sem.security.details.MyUserDetails;
import org.jetbrains.semwork_2sem.services.intefaces.CommentService;
import org.jetbrains.semwork_2sem.services.intefaces.PostService;
import org.jetbrains.semwork_2sem.services.intefaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/post/{post-id}")
    public String getPostPage(@PathVariable("post-id") Long postId,
                              Model model,
                              @AuthenticationPrincipal MyUserDetails currentUser) {
        List<CommentDto> commentsOfPost = commentService.findAllByPostId(postId);

        PostDto post = postService.getByPostId(postId, currentUser.getUser().getId());
        model.addAttribute("post", post);

        model.addAttribute("currentId", currentUser.getUser().getId());
        model.addAttribute("comments", commentsOfPost);
        return "post_page";
    }

    @PostMapping("/comment/{postId}")
    public String addComment(CommentForm commentForm,
                             @PathVariable("postId") Long postId,
                             @AuthenticationPrincipal MyUserDetails currentUser) {
        commentService.addComment(commentForm, currentUser.getUser().getId(), postId);
        return "redirect:/post/" + postId;
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<?> likePost(@PathVariable Long postId,
                                      @AuthenticationPrincipal MyUserDetails currentUser) {
        postService.like(currentUser.getUser().getId(), postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/createPost")
    public String getFilesUploadPage(Model model,
                                     @AuthenticationPrincipal MyUserDetails currentUser) {
        Long currentUserId = currentUser.getUser().getId();
        model.addAttribute("currentId", currentUserId);
        return "post_upload_page";
    }

    @PostMapping("/createPost")
    public String createPost(PostForm postForm,
                             @AuthenticationPrincipal MyUserDetails currentUser,
                             RedirectAttributes redirectAttributes) {
        Long currentUserId = currentUser.getUser().getId();
        if (postForm.getFiles() != null && postForm.getFiles().size() > 10) {
            redirectAttributes.addFlashAttribute("error", "Нельзя добавить больше 10 файлов");
            return "redirect:/createPost";
        }
        try {
            postService.createPost(currentUserId, postForm.getText(), postForm.getFiles());
            return "redirect:/profile/" + currentUserId;
        } catch ( Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка: " + e.getMessage());
            return "redirect:/createPost";
        }
    }



}

package org.jetbrains.semwork_2sem.controllers.api;

import org.jetbrains.semwork_2sem.dto.CommentDto;
import org.jetbrains.semwork_2sem.dto.CommentForm;
import org.jetbrains.semwork_2sem.security.details.MyUserDetails;
import org.jetbrains.semwork_2sem.services.intefaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;


    @GetMapping("/post/{post-id}")
    public ResponseEntity<List<CommentDto>> getAllByPost(@PathVariable("post-id") Long postId) {
        List<CommentDto> commentsByPost = commentService.findAllByPostId(postId);
        return ResponseEntity.ok(commentsByPost);
    }

    @PostMapping("/post/{post-id}")
    public ResponseEntity<Void> addComment(@PathVariable("post-id") Long postId,
                                           @RequestBody CommentForm commentForm,
                                           @AuthenticationPrincipal MyUserDetails currentUser) {
        commentService.addComment(commentForm, currentUser.getUser().getId(), postId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

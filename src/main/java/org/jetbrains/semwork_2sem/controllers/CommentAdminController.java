package org.jetbrains.semwork_2sem.controllers;

import org.jetbrains.semwork_2sem.dto.CommentDto;
import org.jetbrains.semwork_2sem.models.Role;
import org.jetbrains.semwork_2sem.security.details.MyUserDetails;
import org.jetbrains.semwork_2sem.services.intefaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/comments")
@Secured("ROLE_ADMIN")
public class CommentAdminController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllCommentsAdmin(
            @AuthenticationPrincipal MyUserDetails currentUser) {
        if (currentUser.getUser().getRole() != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(commentService.getAllComments());
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteCommentAdmin(
            @PathVariable Long commentId,
            @AuthenticationPrincipal MyUserDetails currentUser) {
        if (currentUser.getUser().getRole() != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        commentService.remove(commentId);
        return ResponseEntity.noContent().build();
    }
}

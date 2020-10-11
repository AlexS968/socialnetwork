package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.CommentRequest;
import main.data.response.CommentResponse;
import main.data.response.base.ListResponse;
import main.data.response.type.ItemDelete;
import main.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("/api/v1/post")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Integer id,
            @RequestBody CommentRequest request
    ) {
        return ResponseEntity.ok(commentService.createComment(id, request));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<ListResponse> showPostComments(
            @PathVariable Integer id,
            @RequestParam(required = true, defaultValue = "0") Integer offset,
            @RequestParam(required = true, defaultValue = "20") Integer itemPerPage
    ) {

        return ResponseEntity.ok(commentService.getPostComments(id, offset, itemPerPage));
    }

    @PutMapping("/{id}/comments/{comment_id}")
    public ResponseEntity<CommentResponse> editPostComment(
            @PathVariable Integer id,
            @PathVariable(value = "comment_id") Integer commentId,
            @RequestBody CommentRequest request
    ){
        return ResponseEntity.ok(commentService.editComment(id, commentId, request));
    }

    @PutMapping("/{id}/comments/{comment_id}/recover")
    public ResponseEntity<CommentResponse> recoverPostComment(
            @PathVariable Integer id,
            @PathVariable(value = "comment_id") Integer commentId
    ) {
        return ResponseEntity.ok(commentService.recoverComment(id, commentId));
    }

    @DeleteMapping("{id}/comments/{comment_id}")
    public ResponseEntity<ItemDelete> deletePostComment(
            @PathVariable Integer id,
            @PathVariable(value = "comment_id") Integer commentId
    ) {
        return ResponseEntity.ok(commentService.deleteComment(id, commentId));
    }

}

package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.CommentRequest;
import main.data.response.CommentResponse;
import main.data.response.PostCommentsResponse;
import main.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

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
    public ResponseEntity<PostCommentsResponse> showPostComments(
            @PathVariable Integer id,
            @RequestParam(required = true, defaultValue = "0") Integer offset,
            @RequestParam(required = true, defaultValue = "20") Integer itemPerPage
    ) {

        return ResponseEntity.ok(commentService.getPostComments(id, offset, itemPerPage));
    }

}

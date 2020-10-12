package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.PostRequest;
import main.data.response.FeedsResponse;
import main.data.response.PostResponse;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    @GetMapping("/feeds")
    public ResponseEntity<?> getFeeds(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int itemPerPage
    ) {
        FeedsResponse response = new FeedsResponse();
        try {
            response = postService.getFeeds(name, offset, itemPerPage);
        } catch (BadRequestException ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Bad request"));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Integer id) {
        return postService.delPost(id);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<PostResponse> editPost(@PathVariable Integer id,
                                                 @RequestParam(name = "publish_date", required = false) Long pubDate,
                                                 @RequestBody PostRequest request
                                      ) {
        return ResponseEntity.ok(postService.editPost(id, pubDate, request));
    }


}

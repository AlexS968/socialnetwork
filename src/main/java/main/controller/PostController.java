package main.controller;

import lombok.AllArgsConstructor;
import main.data.response.FeedsResponse;
import main.data.response.ItemDeleteResponse;
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
    public ResponseEntity<FeedsResponse> getFeeds(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam (required = false, defaultValue = "10") int itemPerPage
    ) {
        return ResponseEntity.ok(postService.getFeeds(name, offset, itemPerPage));
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<ItemDeleteResponse> deletePost(@PathVariable Integer id){
        return ResponseEntity.ok(postService.delPost(id));
    }



}

package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.PostRequest;
import main.data.response.FeedsResponse;
import main.data.response.PostResponse;
import main.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class ProfileController {
    private final PostService postService;

    @PostMapping("/{id}/wall")
    public ResponseEntity<PostResponse> addPostToWall(
            @PathVariable int id,
            @RequestBody PostRequest request,
            @RequestParam(name = "publish_date", required = false) Long pubDate
            ){
        return ResponseEntity.ok(postService.addNewPost(id, request, pubDate));
    }

    @GetMapping("{id}/wall")
    public ResponseEntity<FeedsResponse> showPersonWall(
        @PathVariable int id
    ){
        return ResponseEntity.ok(postService.showWall(id));
    }

}

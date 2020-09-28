package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.PostRequest;
import main.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class ProfileController {
    private final PostService postService;

    @PostMapping("/{id}/wall")
    public ResponseEntity<?> addPostToWall(
            @PathVariable int id,
            @RequestBody PostRequest request
            ){

        return postService.addNewPost(id, request);
    }

    @GetMapping("{id}/wall")
    public ResponseEntity<?> showPersonWall(
        @PathVariable int id
    ){
        return postService.showWall(id);
    }

}

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
            @RequestBody PostRequest request,
            @RequestParam(name = "publish_date", required = false) Long pubDate
            ){

        return postService.addNewPost(id, request, pubDate);
    }

    @GetMapping("/{id}/wall")
    public ResponseEntity<?> showPersonWall(
        @PathVariable int id,
        @RequestParam(defaultValue = "0") int offset,
        @RequestParam(defaultValue = "20") int itemsPerPage
    ){
        return postService.showWall(id, offset, itemsPerPage);
    }

}

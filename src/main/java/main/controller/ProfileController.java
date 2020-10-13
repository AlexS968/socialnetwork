package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.PostRequest;
import main.data.response.base.ListResponse;
import main.data.response.base.Response;
import main.data.response.type.PostInResponse;
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
    public ResponseEntity<Response<PostInResponse>> addPostToWall(
            @PathVariable int id,
            @RequestBody PostRequest request,
            @RequestParam(name = "publish_date", required = false) Long pubDate
            ){
        return ResponseEntity.ok(postService.addNewPost(id, request, pubDate));
    }

    @GetMapping("/{id}/wall")
    public ResponseEntity<ListResponse<PostInResponse>> showPersonWall(
        @PathVariable int id,
        @RequestParam(defaultValue = "0") int offset,
        @RequestParam(defaultValue = "20") int itemsPerPage
    ){
        return ResponseEntity.ok(postService.showWall(id, offset, itemsPerPage));
    }

}

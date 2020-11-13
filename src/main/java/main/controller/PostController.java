package main.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import main.data.request.PostRequest;
import main.data.response.base.ListResponse;
import main.data.response.base.Response;
import main.data.response.type.PostDelete;
import main.data.response.type.PostInResponse;
import main.service.PostServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Api
@Controller
@AllArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final PostServiceImpl postServiceImpl;

    @GetMapping("/feeds")
    public ResponseEntity<ListResponse<PostInResponse>> getFeeds(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int itemPerPage
    ) {
        return ResponseEntity.ok(postServiceImpl.getFeeds(name, offset, itemPerPage));
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Response<PostDelete>> deletePost(@PathVariable Integer id) {
        return ResponseEntity.ok(postServiceImpl.delPost(id));
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<Response<PostInResponse>> editPost(
            @PathVariable Integer id,
            @RequestParam(name = "publish_date", required = false) Long pubDate,
            @RequestBody PostRequest request
    ) {
        return ResponseEntity.ok(postServiceImpl.editPost(id, pubDate, request));
    }
}

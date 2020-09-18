package main.controller;

import main.data.response.FeedsResponse;
import main.model.Post;
import main.repository.PostRepository;
import main.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("api/v1/feeds")
    public ResponseEntity<FeedsResponse> getFeeds(
            @RequestParam(required = false, defaultValue = "") String name,
            //@RequestParam int offset,
            //@RequestParam (required = true, defaultValue = "20") int itemPerPage,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Post> posts;
        //TODO fix pagination
        if (name != null && !name.isEmpty()){
            posts = postService.getPostsByTitle(name, pageable);
        } else {
            posts = postService.getAllPosts(pageable);
        }
        FeedsResponse response = new FeedsResponse(pageable.getOffset(), pageable.getPageSize());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

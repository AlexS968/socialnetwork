package main.controller;

import main.data.response.FeedsResponse;
import main.model.Post;
import main.repository.PostRepository;
import main.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
//            @RequestParam(required = false, defaultValue = "0") int offset,
//            @RequestParam (required = false, defaultValue = "10") int itemPerPage,
            Model model
    ) {
        int offset = 0;
        int itemPerPage = 10;
        FeedsResponse response = postService.getFeeds(name, offset, itemPerPage);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

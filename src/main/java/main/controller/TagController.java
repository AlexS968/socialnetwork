package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.TagRequest;
import main.data.response.ListTagResponse;
import main.data.response.TagCreateResponse;
import main.data.response.TagDeleteResponse;
import main.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    @GetMapping("/")
    public ResponseEntity<ListTagResponse> getPostTags(@RequestParam String tag,
                                                       @RequestParam int offset,
                                                       @RequestParam(required = false, defaultValue = "20") int itemsPerPage) {
        return ResponseEntity.ok(tagService.getPostTags(tag, offset, itemsPerPage));
    }

    @PostMapping("/")
    public ResponseEntity<TagCreateResponse> CreateTag(@RequestBody TagRequest request) {
        return ResponseEntity.ok(tagService.createTag(request));
    }

    @DeleteMapping("/")
    public ResponseEntity<TagDeleteResponse> DeleteTag(@RequestParam int id) {
        return ResponseEntity.ok(tagService.deleteTag(id));
    }
}

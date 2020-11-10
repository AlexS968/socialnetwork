package main.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import main.data.request.TagRequest;
import main.data.response.base.ListResponse;
import main.data.response.base.Response;
import main.data.response.type.DataMessage;
import main.data.response.type.SingleTag;
import main.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Api
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    @GetMapping("/")
    public ResponseEntity<ListResponse<SingleTag>> getPostTags(@RequestParam String tag,
                                                               @RequestParam int offset,
                                                               @RequestParam(required = false, defaultValue = "20") int itemsPerPage) {
        return ResponseEntity.ok(tagService.getPostTags(tag, offset, itemsPerPage));
    }

    @PostMapping("/")
    public ResponseEntity<Response<SingleTag>> CreateTag(@RequestBody TagRequest request) {
        return ResponseEntity.ok(tagService.createTag(request));
    }

    @DeleteMapping("/")
    public ResponseEntity<Response<DataMessage>> DeleteTag(@RequestParam int id) {
        return ResponseEntity.ok(tagService.deleteTag(id));
    }
}

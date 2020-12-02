package main.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import main.data.request.LikeRequest;
import main.data.response.base.Response;
import main.data.response.type.LikesWithUsers;
import main.service.LikesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LikesController {
    private final LikesService likesService;

    @GetMapping("/liked")
    public ResponseEntity<Response<LikesWithUsers>> isLiked(
            @RequestParam(name = "user_id", required = false) Integer userId,
            @RequestParam(name = "item_id") int itemId,
            @RequestParam String type
    ) {
        return ResponseEntity.ok(likesService.isLiked(userId, itemId, type));
    }

    @GetMapping("/likes")
    public ResponseEntity<Response<LikesWithUsers>> getLikes(
            @RequestParam(name = "item_id") int itemId,
            @RequestParam String type
    ) {
        return ResponseEntity.ok(likesService.getLikes(itemId, type));
    }

    @PutMapping("/likes")
    public ResponseEntity<Response<LikesWithUsers>> setLike(
            @RequestBody LikeRequest request
    ) {
        return ResponseEntity.ok(likesService.setLike(request));
    }

    @DeleteMapping("/likes")
    public ResponseEntity<Response<LikesWithUsers>> deleteLike(
            @RequestParam(name = "item_id") int itemId,
            @RequestParam String type
    ) {
        return ResponseEntity.ok(likesService.deleteLike(itemId, type));
    }
}

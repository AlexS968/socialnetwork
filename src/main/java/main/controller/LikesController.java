package main.controller;

import lombok.RequiredArgsConstructor;
import main.data.request.LikeRequest;
import main.data.response.base.Response;
import main.data.response.type.LikesWithUsers;
import main.service.LikesService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LikesController {
    private final LikesService likesService;

    @GetMapping("/liked")
    public ResponseEntity<?> isLiked(
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
    public ResponseEntity<?> deleteLike(
            @RequestParam(name = "item_id") int itemId,
            @RequestParam String type
    ) {
        return ResponseEntity.ok(likesService.deleteLike(itemId, type));
    }
}

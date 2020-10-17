package main.service;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import main.data.request.LikeRequest;
import main.data.response.base.Response;
import main.data.response.type.LikesWithUsers;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.model.*;
import main.repository.LikesRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {
    private final LikesRepository likesRepository;
    private final PersonServiceImpl personService;
    private final CommentService commentService;
    private final PostService postService;

    public Response<LikesWithUsers> isLiked(Integer userId, int itemId, String type) {
        Person person= userId == null ? personService.getAuthUser() : personService.getPerson(userId);
        Optional<Like> likeOptional;
        if (type.equals("Post") || type.equals("Comment")) {
            likeOptional = likesRepository.findByItemIdAndPersonIdAndType(itemId, person.getId(), LikeType.valueOf(type.toUpperCase()));
        } else {
            throw new BadRequestException(new ApiError("invalid_request", "Выбран не пост или комментарий"));
        }
        return new Response<>(new LikesWithUsers(likeOptional.isPresent() ? 1 : 0, Collections.emptyList()));
    }

    public Response<LikesWithUsers> getLikes(int itemId, String type) {
        personService.isAuthenticated();
        List<Like> likes = likesRepository.findAllByItemIdAndType(itemId, LikeType.valueOf(type.toUpperCase()));
        List<Integer> userIds = new ArrayList<>();
        likes.forEach(l -> userIds.add(l.getPerson().getId()));
        return new Response<>(new LikesWithUsers(likes.size(), userIds));
    }

    public Response<LikesWithUsers> setLike(LikeRequest request) {
        Person person = personService.getAuthUser();
        if (isLiked(person.getId(), request.getItemId(), request.getType()).getData().getLikes() == 0) {
            Like like = new Like();
            if (request.getType().equals("Post")) {
                Post post = postService.getPost(request.getItemId());
                like.setItemId(post.getId());
                like.setType(LikeType.POST);
            } else if (request.getType().equals("Comment")) {
                PostComment comment = commentService.getComment(request.getItemId());
                like.setItemId(comment.getId());
                like.setType(LikeType.COMMENT);
            } else {
                throw new BadRequestException(new ApiError("invalid_request", "Выбран не пост или комментарий"));
            }
            like.setPerson(person);
            like.setTime(Instant.now());
            likesRepository.save(like);
        }
        return getLikes(request.getItemId(), request.getType());
    }

    @Transactional
    public Response<LikesWithUsers> deleteLike(int itemId, String type) {
        Person person = personService.getAuthUser();
        if (type.equals("Post") || type.equals("Comment")) {
            try {
                likesRepository.deleteByItemIdAndPersonIdAndType(itemId, person.getId(), LikeType.valueOf(type.toUpperCase()));
            } catch (Exception ex) {
                throw new BadRequestException(new ApiError("invalid_request", "Объект не найден"));
            }
        } else {
            throw new BadRequestException(new ApiError("invalid_request", "Выбран не пост или комментарий"));
        }
        return getLikes(itemId, type);
    }

    public Like getLike(int itemId, LikeType type) {
        Optional<Like> likeOptional;
        if (type.equals(LikeType.POST) || type.equals(LikeType.COMMENT)) {
            likeOptional = likesRepository.findByItemIdAndType(itemId, type);
            if (likeOptional.isPresent()) {
                return likeOptional.get();
            } else {
                throw new BadRequestException(new ApiError("invalid_request", "Объект не найден"));
            }
        } else {
            throw new BadRequestException(new ApiError("invalid_request", "Выбран не пост или комментарий"));
        }
    }
}

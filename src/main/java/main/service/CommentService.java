package main.service;

import lombok.AllArgsConstructor;
import main.controller.ApiPlatformController;
import main.data.PersonPrincipal;
import main.data.request.CommentRequest;
import main.data.response.CommentResponse;
import main.data.response.PostCommentsResponse;
import main.data.response.type.CommentInResponse;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.model.Person;
import main.model.PostComment;
import main.repository.PostCommentRepository;
import main.repository.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {

    private final PostCommentRepository commentRepository;
    private final PostRepository postRepository;


    public CommentResponse createComment(Integer postId, CommentRequest request) {
        CommentResponse response = new CommentResponse();

        PostComment postComment = new PostComment();
        postComment.setCommentText(request.getCommentText());
        if (request.getParentId() != null) {
            Optional<PostComment> postCommentOpt = commentRepository.findById(request.getParentId());
            postCommentOpt.ifPresent(postComment::setParent);
        }
        PersonPrincipal currentUser = (PersonPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        postComment.setAuthor(currentUser.getPerson());
        postComment.setTime(Instant.now());
        postComment.setBlocked(false);
        postComment.setPost(postRepository.findById(postId)
                .orElseThrow(() -> new BadRequestException(
                        new ApiError("invalid_request", "ссылка на несуществующий пост")
                )));
        commentRepository.save(postComment);

        CommentInResponse commentInResponse = new CommentInResponse(postComment);
        response.setData(commentInResponse);

        return response;
    }

    public PostCommentsResponse getPostComments(Integer postId, Integer offset, Integer itemPerPage) {
        PostCommentsResponse response = new PostCommentsResponse();

        List<PostComment> list = commentRepository.findAllByPostId(postId);

        response.setTotal(list.size());
        response.setOffset(offset);
        response.setPerPage(itemPerPage);

        List<CommentInResponse> data = new ArrayList<>();
        list.forEach(comment -> data.add(new CommentInResponse(comment)));
        response.setData(data);

        return response;
    }
}


package main.service;

import lombok.AllArgsConstructor;
import main.data.PersonPrincipal;
import main.data.request.CommentRequest;
import main.data.response.CommentResponse;
import main.data.response.base.ListResponse;
import main.data.response.type.CommentInResponse;
import main.data.response.type.ItemDelete;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.model.Post;
import main.model.PostComment;
import main.repository.PostCommentRepository;
import main.repository.PostRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public ListResponse<CommentInResponse> getPostComments(Integer postId, Integer offset, Integer itemPerPage) {
        List<PostComment> comments = commentRepository.findAllByPostId(postId);
        List<CommentInResponse> list = comments.stream().map(CommentInResponse::new).collect(Collectors.toList());
        return new ListResponse<>(list, list.size(), offset, itemPerPage);
    }

    //Excess??
    public List<CommentInResponse> getCommentsList (List<Post> posts) {
        Set<Integer> list = posts.stream().map(Post::getId).collect(Collectors.toSet());
        List<PostComment> comments = commentRepository.getCommentsByList(list);
        return comments.stream().map(CommentInResponse::new).collect(Collectors.toList());
    }

    public CommentResponse editComment(Integer id, Integer commentId, CommentRequest request) {
        CommentResponse response = new CommentResponse();

        PostComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BadRequestException(
                        new ApiError("invalid_request", "Несуществующий комментарий")
                ));

        comment.setCommentText(request.getCommentText());
        commentRepository.save(comment);

        return response;
    }

    public CommentResponse recoverComment(Integer postId, Integer commentId) {
        CommentResponse response = new CommentResponse();

        //TODO complete recoverComment

        return response;
    }

    public ItemDelete deleteComment(Integer postId, Integer commentId) {
        ItemDelete response = new ItemDelete();

        //TODO complete deleteComment
        PostComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BadRequestException(
                        new ApiError("invalid_request","Несуществующий коммент"))
                );
        commentRepository.delete(comment);
        response.setId(commentId);
        
        return response;
    }
}


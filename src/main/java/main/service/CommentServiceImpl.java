package main.service;

import lombok.RequiredArgsConstructor;
import main.data.request.CommentRequest;
import main.data.response.CommentResponse;
import main.data.response.base.ListResponse;
import main.data.response.type.CommentInResponse;
import main.data.response.type.ItemDelete;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.model.Person;
import main.model.Post;
import main.model.PostComment;
import main.repository.PostCommentRepository;
import main.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostCommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PersonService personService;
    private final NotificationService notificationService;

    @Override
    public CommentResponse createComment(Integer postId, CommentRequest request) {
        CommentResponse response = new CommentResponse();

        PostComment postComment = new PostComment();
        postComment.setCommentText(request.getCommentText());
        if (request.getParentId() != null) {
            Optional<PostComment> postCommentOpt = commentRepository.findById(request.getParentId());
            postCommentOpt.ifPresent(postComment::setParent);
        }

        Person currentUser = personService.getAuthUser();
        postComment.setAuthor(currentUser);
        postComment.setTime(Instant.now());
        postComment.setBlocked(false);
        postComment.setPost(postRepository.findById(postId)
                .orElseThrow(() -> new BadRequestException(
                        new ApiError("invalid_request", "ссылка на несуществующий пост")
                )));
        commentRepository.save(postComment);

        //создаем notification
        notificationService.setNotification(postComment);
        CommentInResponse commentInResponse = new CommentInResponse(postComment, new ArrayList<>(), currentUser.getId());
        response.setData(commentInResponse);
        return response;
    }

    @Override
    public ListResponse<CommentInResponse> getPostComments(Integer postId, Integer offset, Integer itemPerPage) {
        Person currentUser = personService.getAuthUser();
        List<PostComment> comments = commentRepository.findAllByPostId(postId);
        List<CommentInResponse> list = getComments(comments, currentUser);
        return new ListResponse<>(list, list.size(), offset, itemPerPage);
    }

    private List<CommentInResponse> getComments(List<PostComment> comments, Person currentUser) {
        List<CommentInResponse> commentsDto = comments.stream().map(p -> new CommentInResponse(p, currentUser.getId())).collect(Collectors.toList());
        List<CommentInResponse> commentsResult = commentsDto.stream()
                .filter(commentInResponse -> commentInResponse.getParentId() == 0).collect(Collectors.toList());
        for (CommentInResponse commentDto : commentsResult) {
            List<CommentInResponse> subComments = getSublistComment(commentsDto, commentDto.getId());
            commentDto.setSubComments(subComments);
        }
        return commentsResult;
    }

    private List<CommentInResponse> getSublistComment(List<CommentInResponse> comments, long commentId) {
        List<CommentInResponse> subComments = new ArrayList<>();

        for (CommentInResponse comment : comments) {
            if (comment.getParentId() == commentId) {
                subComments.add(comment);
            }
        }

        return subComments;
    }

    @Override
    public List<CommentInResponse> getCommentsList(List<Post> posts) {
        Person currentUser = personService.getAuthUser();
        Set<Integer> list = posts.stream().map(Post::getId).collect(Collectors.toSet());
        List<PostComment> comments = commentRepository.getCommentsByList(list);
        return getComments(comments, currentUser);
    }

    @Override
    public CommentResponse editComment(Integer id, Integer commentId, CommentRequest request) {
        CommentResponse response = new CommentResponse();

        PostComment comment = getComment(commentId);

        comment.setCommentText(request.getCommentText());
        commentRepository.save(comment);
        return response;
    }

    @Override
    public CommentResponse recoverComment(Integer postId, Integer commentId) {
        CommentResponse response = new CommentResponse();

        //TODO complete recoverComment

        return response;
    }

    @Override
    public ItemDelete deleteComment(Integer postId, Integer commentId) {
        ItemDelete response = new ItemDelete();

        PostComment comment = getComment(commentId);
        deleteSubComment(commentId);
        commentRepository.delete(comment);
        response.setId(commentId);
        return response;
    }

    private void deleteSubComment(Integer commentId) {
        Set<PostComment> subComments = commentRepository.subCommentsG(commentId);
        commentRepository.deleteAll(subComments);
    }

    @Override
    public PostComment getComment(int itemId) {
        Optional<PostComment> optionalPostComment = commentRepository.findById(itemId);
        if (optionalPostComment.isPresent()) {
            return optionalPostComment.get();
        } else {
            throw new BadRequestException(new ApiError("invalid_request", "Несуществующий коммент"));
        }
    }
}

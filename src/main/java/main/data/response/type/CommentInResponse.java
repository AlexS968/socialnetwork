package main.data.response.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.model.PostComment;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentInResponse {
    @JsonProperty(value = "parent_id")
    private Integer parentId;
    @JsonProperty(value = "comment_text")
    private String commentText;
    private long id;
    @JsonProperty(value = "post_id")
    private Integer postId;
    private long time;
    @JsonProperty(value = "author")
    private MeProfile authorId;
    @JsonProperty(value = "is_blocked")
    private boolean blocked;

    @JsonProperty("sub_comments")
    private List<CommentInResponse> subComments;

    @JsonProperty("like_count")
    private int likeCount;

    @JsonProperty("is_my_like")
    private boolean isMyLike;

    public CommentInResponse(PostComment comment, List<CommentInResponse> subComments) {
        parentId = comment.getParent() != null ? comment.getParent().getId() : 0;
        commentText = comment.getCommentText();
        id = comment.getId();
        postId = comment.getPost().getId();
        time = Instant.now().toEpochMilli();
        authorId = new MeProfile(comment.getAuthor());
        blocked = comment.isBlocked();
        this.subComments = subComments;
        likeCount = 0;
        isMyLike = false;
    }

    public CommentInResponse(PostComment comment) {
        parentId = comment.getParent() != null ? comment.getParent().getId() : 0;
        commentText = comment.getCommentText();
        id = comment.getId();
        postId = comment.getPost().getId();
        time = Instant.now().toEpochMilli();
        authorId = new MeProfile(comment.getAuthor());
        blocked = comment.isBlocked();
        //this.subComments = subComments;
        likeCount = 0;
        isMyLike = false;
    }
}

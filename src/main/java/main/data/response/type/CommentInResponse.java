package main.data.response.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.model.PostComment;

import java.time.Instant;

@Data
@NoArgsConstructor
public class CommentInResponse {
    @JsonProperty(value = "parent_id")
    private Integer parentId;
    @JsonProperty(value = "comment_text")
    private String commentText;
    private long id;
    @JsonProperty(value = "post_id")
    private String postId;
    private long time;
    @JsonProperty(value = "author_id")
    private long authorId;
    @JsonProperty(value = "is_blocked")
    private boolean blocked;

    public CommentInResponse(PostComment comment) {
        parentId = comment.getParent() != null ? comment.getParent().getId() : 0;
        commentText = comment.getCommentText();
        id = comment.getId();
        postId = String.valueOf(comment.getPost().getId());
        time = Instant.now().toEpochMilli();
        authorId = comment.getAuthor().getId();
        blocked = comment.isBlocked();
    }
}

package main.data.response.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentInResponse {
    @JsonProperty(value = "parent_id")
    private long parentId;
    @JsonProperty(value = "comment_text")
    private String commentText;
    private long id;
    @JsonProperty(value = "post_id")
    private long postId;
    private long time;
    @JsonProperty(value = "author_id")
    private long authorId;
    @JsonProperty(value = "is_blocked")
    private boolean blocked;
}

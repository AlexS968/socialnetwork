package main.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CommentDto {
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

    public CommentDto(){
        parentId = 0;
        commentText = "Комментарий к посту";
        id = 111;
        postId = 1;
        time = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        authorId = 1;
        blocked = false;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

}

package main.data.response.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.model.Post;
import main.model.PostType;
import main.model.Tag;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInResponse {
    private Integer id;
    private Instant time;
    private MeProfile author;
    private String title;
    @JsonProperty(value = "post_text")
    private String postText;
    @JsonProperty(value = "is_blocked")
    private boolean isBlocked;
    private int likes;
    private List<CommentInResponse> comments;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PostType type;
    private List<SingleTag> tags;

    public PostInResponse(Post post) {
        id = post.getId();
        time = post.getTime();
        author = new MeProfile(post.getAuthor());
        title = post.getTitle();
        postText = post.getPostText();
        isBlocked = post.isBlocked();
        likes = post.getLikes() != null ? post.getLikes().size() : 0;
        comments = new ArrayList<>();
        tags = getTags(post);
    }

    private List<SingleTag> getTags(Post post) {
        List<SingleTag> tags = new ArrayList<>();
        post.getTags().forEach(t -> tags.add(new SingleTag(t.getTag().getId(), t.getTag().getTag())));
        return tags;
    }
}

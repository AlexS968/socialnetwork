package main.data.response.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.model.Post;
import main.model.PostType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInResponse {
    private Integer id;
    private long time;
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
    private List<String> tags;

    public PostInResponse(Post post, List<CommentInResponse> commentsList) {
        id = post.getId();
        time = post.getTime().toEpochMilli();
        author = new MeProfile(post.getAuthor());
        title = post.getTitle();
        postText = post.getPostText();
        isBlocked = post.isBlocked();
        likes = post.getLikes() != null ? post.getLikes().size() : 0;
        comments = commentsList.stream()
                .filter(commentInResponse -> commentInResponse.getPostId() == id)
                .collect(Collectors.toList());
        tags = getTags(post);
    }

    private List<String> getTags(Post post) {
        List<String> tags = new ArrayList<>();
        post.getTags().forEach(t -> tags.add(t.getTag().getTag()));
        return tags;
    }
}

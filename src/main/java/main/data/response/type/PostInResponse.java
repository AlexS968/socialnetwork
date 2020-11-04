package main.data.response.type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.model.Like;
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
    private PersonProfile author;
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
    @JsonProperty(value = "my_like")
    private boolean myLike;

    public PostInResponse(Post post, List<CommentInResponse> commentsList, int currentUserId) {
        id = post.getId();
        time = post.getTime().toEpochMilli();
        author = new PersonProfile(post.getAuthor());
        title = post.getTitle();
        postText = post.getPostText();
        isBlocked = post.isBlocked();
        likes = post.getLikes() != null ? post.getLikes().size() : 0;
        comments = commentsList.stream()
                .filter(commentInResponse -> commentInResponse.getPostId() == id)
                .collect(Collectors.toList());
        tags = getTags(post);
        myLike = likes != 0 && post.getLikes().stream().anyMatch(l -> l.getPerson().getId() == currentUserId);
    }

    private List<String> getTags(Post post) {
        List<String> tags = new ArrayList<>();
        post.getTags().forEach(t -> tags.add(t.getTag().getTag()));
        return tags;
    }
}

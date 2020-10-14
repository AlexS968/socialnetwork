package main.data.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import main.data.response.base.ListResponse;
import main.data.response.type.CommentInResponse;
import main.data.response.type.PostInResponse;
import main.model.*;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data

public class FeedsResponse extends ListResponse<PostInResponse> {

    public FeedsResponse(Page<Post> posts, List<CommentInResponse> commentList, int authorId){
        this.setOffset(posts.getNumber() * posts.getNumberOfElements());
        this.setPerPage(posts.getNumberOfElements());
        this.setTimestamp(Instant.now().toEpochMilli());
        this.setTotal(posts.getTotalElements());
        this.setData(new ArrayList<>());

        for (Post item : posts.getContent()) {
            PostInResponse postInResponse = new PostInResponse(item, commentList);
            if (item.getTime().isBefore(Instant.now())) {
                postInResponse.setType(PostType.POSTED);
            } else {
                postInResponse.setType(PostType.QUEUED);
            }
            if (!(postInResponse.getType() == PostType.QUEUED && postInResponse.getAuthor().getId() != authorId)) {
                this.add(postInResponse);
            }
        }
    }
}

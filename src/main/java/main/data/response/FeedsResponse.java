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
public class FeedsResponse<T> extends ListResponse<T> {

    public FeedsResponse(Page<Post> posts, List<CommentInResponse> commentList){
        this.setOffset(posts.getNumber() * posts.getNumberOfElements());
        this.setPerPage(posts.getNumberOfElements());
        this.setTotal(posts.getTotalElements());
        List<T> data = new ArrayList<>();

        for (Post item : posts.getContent()) {
            PostInResponse postInResponse = new PostInResponse(item, commentList);
            if (item.getTime().isBefore(Instant.now())) {
                postInResponse.setType(PostType.POSTED);
            } else {
                postInResponse.setType(PostType.QUEUED);
            }
            data.add((T) postInResponse);
        }

        this.setData(data);
    }
}

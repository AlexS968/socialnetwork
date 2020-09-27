package main.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.data.response.base.ListResponse;
import main.data.response.type.PostInResponse;
import main.model.*;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FeedsResponse extends ListResponse {

    @JsonProperty(value = "data")
    private List<PostInResponse> postsList;

    public FeedsResponse(Page<Post> posts){
        this.setOffset(posts.getNumber() * posts.getNumberOfElements());
        this.setPerPage(posts.getNumberOfElements());
        this.setError("string");
        this.setTimestamp(Instant.now().toEpochMilli());
        this.setTotal(posts.getTotalElements());
        postsList = new ArrayList<>();

        for (Post item : posts.getContent()) {
            postsList.add(new PostInResponse(item));
        }
    }
}

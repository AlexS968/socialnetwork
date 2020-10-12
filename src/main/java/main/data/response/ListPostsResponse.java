package main.data.response;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import main.data.response.type.PostInResponse;
import main.model.Post;
import org.springframework.data.domain.Page;

@Data
public class ListPostsResponse {
  private String error = "";
  private long timestamp = Instant.now().toEpochMilli();
  private long total;
  private int offset;
  private int perPage;

  private List<PostInResponse> data = new ArrayList<>();

  public ListPostsResponse(Page<Post> posts) {

    this.setOffset(posts.getNumber() * posts.getNumberOfElements());
    this.setPerPage(posts.getNumberOfElements());
    this.setError("string");
    this.setTimestamp(Instant.now().toEpochMilli());
    this.setTotal(posts.getTotalElements());

    posts.forEach(p ->
    {{
      data.add(new PostInResponse(p));
      } });
  }
}



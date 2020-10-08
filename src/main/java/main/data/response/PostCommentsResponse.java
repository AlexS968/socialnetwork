package main.data.response;

import lombok.Data;
import main.data.response.base.ListResponse;
import main.data.response.type.CommentInResponse;

import java.util.List;

@Data
public class PostCommentsResponse extends ListResponse {
    private List<CommentInResponse> data;


}

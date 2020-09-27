package main.data.response;

import lombok.Data;
import main.data.response.base.RecordResponse;
import main.data.response.type.PostDelete;

@Data
public class PostDeleteResponse extends RecordResponse {
    private PostDelete data;
}

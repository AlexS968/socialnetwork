package main.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.data.response.base.RecordResponse;
import main.data.response.type.PostInResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse extends RecordResponse {
    private PostInResponse data;
}

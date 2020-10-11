package main.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PostRequest {
    private String title;
    @JsonProperty(value = "post_text")
    private String postText;
    private List<String> tags;
}

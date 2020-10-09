package main.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DialogAddRequest {
    @JsonProperty("user_ids")
    private List<Integer> userIds;
}
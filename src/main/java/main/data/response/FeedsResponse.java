package main.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.data.dto.PostDto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class FeedsResponse {
    private String error;
    private long timestamp;
    private long total;
    private long offset;
    private long perPage;
    @JsonProperty(value = "data")
    private List<PostDto> postsList;

    public FeedsResponse(long offset, long perPage){
        this.offset = offset;
        this.perPage = perPage;

        error = "string";
        timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        total = 15;

        postsList = new ArrayList<>();

        for (int i = 1; i < 15; i++) {
            postsList.add(new PostDto());

        }

    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public List<PostDto> getPostsList() {
        return postsList;
    }

    public void setPostsList(List<PostDto> postsList) {
        this.postsList = postsList;
    }
}

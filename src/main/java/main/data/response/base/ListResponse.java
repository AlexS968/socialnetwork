package main.data.response.base;

import lombok.Data;

import java.time.Instant;

@Data
public abstract class ListResponse {
    private String error = "";
    private long timestamp = Instant.now().toEpochMilli();
    private long total;
    private int offset;
    private int perPage;
}

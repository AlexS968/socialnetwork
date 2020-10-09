package main.data.response.base;

import lombok.Data;

import java.time.Instant;

@Data
public class ListResponse<T> {
    private String error = "";
    private long timestamp = Instant.now().toEpochMilli();
    private long total;
    private int offset;
    private int perPage;
    private T data;
}

package main.data.response.base;

import lombok.Data;

import java.time.Instant;

@Data
public class Response<T> {
    private String error = "";
    private long timestamp = Instant.now().toEpochMilli();
    private T data;
}

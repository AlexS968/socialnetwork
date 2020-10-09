package main.data.response.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class Response<T> {
    private String error = "";
    private long timestamp = Instant.now().toEpochMilli();
    private T data;

    public Response(T data) {
        this.data = data;
    }
}

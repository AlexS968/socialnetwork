package main.data.response;

import lombok.Data;

import java.time.Instant;

@Data
abstract class ListResponse {
    private String error = "";
    private long timestamp = Instant.now().toEpochMilli();
    private long total;
    private int offset;
    private int perPage;
}

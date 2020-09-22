package main.data.response.base;

import lombok.Data;

import java.time.Instant;

@Data
public abstract class RecordResponse {
    private String error = "";
    private long timestamp = Instant.now().toEpochMilli();
}

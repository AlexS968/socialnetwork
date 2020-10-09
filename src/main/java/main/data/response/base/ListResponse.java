package main.data.response.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import main.data.response.type.DialogList;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
public class ListResponse<T> {
    private String error = "";
    private long timestamp = Instant.now().toEpochMilli();
    private long total;
    private int offset;
    private int perPage;
    private List<T> data;

    public ListResponse(List<T> data) {
        this.data = data;
    }

    public void add(List<T> data) {
        this.data.addAll(data);
    }

    public ListResponse(List<T> data, long total, int offset, int perPage) {
        this.data = data;
        this.total = total;
        this.offset = offset;
        this.perPage = perPage;
    }
}

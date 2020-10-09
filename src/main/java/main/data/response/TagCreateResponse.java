package main.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import main.data.response.base.RecordResponse;
import main.data.response.type.SingleTag;

@Data
@AllArgsConstructor
public class TagCreateResponse extends RecordResponse {
    private SingleTag data;
}

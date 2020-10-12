package main.data.response;

import lombok.Data;
import main.data.response.base.RecordResponse;
import main.data.response.type.DataMessage;

@Data
public class TagDeleteResponse extends RecordResponse {
    private DataMessage message;
}

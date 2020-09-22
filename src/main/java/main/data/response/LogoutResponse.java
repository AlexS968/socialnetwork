package main.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import main.data.response.base.RecordResponse;
import main.data.response.type.MessageInLogout;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class LogoutResponse extends RecordResponse {
    private MessageInLogout data;
}

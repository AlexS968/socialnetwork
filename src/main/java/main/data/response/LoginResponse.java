package main.data.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import main.data.response.base.RecordResponse;
import main.data.response.type.PersonInLogin;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponse extends RecordResponse {
    private PersonInLogin data;
}

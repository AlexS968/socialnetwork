package main.data.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PasswordRecoveryRequest {
    private String email;
}

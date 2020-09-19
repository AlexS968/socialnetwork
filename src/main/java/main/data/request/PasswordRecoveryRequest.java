package main.data.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordRecoveryRequest {
    private String email;
}

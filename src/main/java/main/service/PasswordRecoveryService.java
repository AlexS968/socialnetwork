package main.service;

import main.data.request.PasswordRecoveryRequest;
import main.data.response.PasswordRecoveryResponse;

public interface PasswordRecoveryService {

    PasswordRecoveryResponse restorePassword(PasswordRecoveryRequest request);
}

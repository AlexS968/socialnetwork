package main.service;

import main.data.request.PasswordRecoveryRequest;
import main.data.request.PasswordSetRequest;
import main.data.response.InfoResponse;

public interface PasswordService {

    InfoResponse restorePassword(PasswordRecoveryRequest request);

    InfoResponse setPassword(PasswordSetRequest request, String referer);

}

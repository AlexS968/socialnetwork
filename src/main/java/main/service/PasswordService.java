package main.service;

import main.data.request.PasswordRecoveryRequest;
import main.data.request.PasswordSetRequest;
import main.data.response.InfoResponse;

public interface PasswordService {

    InfoResponse restorePassword(PasswordRecoveryRequest request, String link);

    InfoResponse setPassword(PasswordSetRequest request, String referer);

    InfoResponse changePassOrEmail(String subject, String link);

    InfoResponse setEmail(PasswordRecoveryRequest request);
}

package main.controller;

import lombok.RequiredArgsConstructor;
import main.data.request.PasswordRecoveryRequest;
import main.data.request.PasswordSetRequest;
import main.data.request.RegistrationRequest;
import main.data.response.NotificationSettingsResponse;
import main.data.response.RegistrationResponse;
import main.data.response.base.Response;
import main.data.response.type.InfoInResponse;
import main.service.NotificationService;
import main.service.PasswordService;
import main.service.RegistrationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class ApiAccountController {

    private final PasswordService passwordService;
    private final RegistrationService registrationService;
    private final NotificationService notificationService;

    @Value("${linkToChange.password}")
    public String passwordChangeLink;
    @Value("${linkToChange.email}")
    public String emailChangeLink;

    @GetMapping("/notifications")
    public ResponseEntity<Response<Set<NotificationSettingsResponse>>> getListOfNotifications() {
        return ResponseEntity.ok(notificationService.getSettings());
    }

    @PutMapping(value = "/password/set")
    public ResponseEntity<Response<InfoInResponse>> setPassword(
            @RequestHeader(name = "Referer") String referer,
            @RequestBody PasswordSetRequest request) {
        return ResponseEntity.ok(passwordService.setPassword(request, referer));
    }

    @PutMapping(value = "/password/recovery")
    public ResponseEntity<Response<InfoInResponse>> recovery(
            @RequestBody PasswordRecoveryRequest request) {
        return ResponseEntity.ok(passwordService.restorePassword(request, passwordChangeLink));
    }

    @PutMapping(value = "/password/change")
    public ResponseEntity<Response<InfoInResponse>> passChange() {
        return ResponseEntity.ok(passwordService.changePassOrEmail("пароля", passwordChangeLink));
    }

    @PutMapping(value = "/email/change")
    public ResponseEntity<Response<InfoInResponse>> emailChange() {
        return ResponseEntity.ok(passwordService.changePassOrEmail("email", emailChangeLink));
    }

    @PutMapping(value = "/email")
    public ResponseEntity<Response<InfoInResponse>> setEmail(
            @RequestBody PasswordRecoveryRequest request) {
        return ResponseEntity.ok(passwordService.setEmail(request));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RegistrationResponse> registration(
            @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(registrationService.registrationNewPerson(request));
    }
}

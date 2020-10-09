package main.controller;

import lombok.RequiredArgsConstructor;
import main.data.request.PasswordRecoveryRequest;
import main.data.request.PasswordSetRequest;
import main.data.request.RegistrationRequest;
import main.data.response.InfoResponse;
import main.data.response.RegistrationResponse;
import main.service.PasswordServiceImpl;
import main.service.RegistrationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class ApiAccountController {
    private final PasswordServiceImpl passwordService;
    private final RegistrationService registrationService;

    @Value("${linkToChange.password}")
    public String passwordChangeLink;
    @Value("${linkToChange.email}")
    public String emailChangeLink;

    @PutMapping(value = "/password/set")
    public ResponseEntity<InfoResponse> setPassword(
            @RequestHeader(name = "Referer") String referer,
            @RequestBody PasswordSetRequest request) {
        return ResponseEntity.ok(passwordService.setPassword(request, referer));
    }

    @PutMapping(value = "/password/recovery")
    public ResponseEntity<InfoResponse> recovery(
            @RequestBody PasswordRecoveryRequest request) {
        return ResponseEntity.ok(passwordService.restorePassword(request, passwordChangeLink));
    }

    @PutMapping(value = "/password/change")
    public ResponseEntity<InfoResponse> passChange() {
        return ResponseEntity.ok(passwordService.changePassOrEmail("пароля", passwordChangeLink));
    }

    @PutMapping(value = "/email/change")
    public ResponseEntity<InfoResponse> emailChange() {
        return ResponseEntity.ok(passwordService.changePassOrEmail("email", emailChangeLink));
    }

    @PutMapping(value = "/email")
    public ResponseEntity<InfoResponse> setEmail(
            @RequestBody PasswordRecoveryRequest request) {
        return ResponseEntity.ok(passwordService.setEmail(request));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RegistrationResponse> registration(
            @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(registrationService.registrationNewPerson(request));
    }
}

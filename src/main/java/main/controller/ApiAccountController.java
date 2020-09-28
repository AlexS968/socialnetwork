package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.PasswordRecoveryRequest;
import main.data.request.PasswordSetRequest;
import main.data.request.RegistrationRequest;
import main.data.response.InfoResponse;
import main.data.response.RegistrationResponse;
import main.service.PasswordServiceImpl;
import main.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/account")
public class ApiAccountController {
    private final PasswordServiceImpl passwordService;
    private final RegistrationService registrationService;

    @PutMapping(value = "/password/set")
    public ResponseEntity<InfoResponse> set(
            @RequestBody PasswordSetRequest request) {
        return ResponseEntity.ok(passwordService.setPassword(request));
    }

    @PutMapping(value = "/password/recovery")
    public ResponseEntity<InfoResponse> recovery(
            @RequestBody PasswordRecoveryRequest request) {
        return ResponseEntity.ok(passwordService.restorePassword(request));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RegistrationResponse> registration(
            @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(registrationService.registrationNewPerson(request));
    }
}

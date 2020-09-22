package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.PasswordRecoveryRequest;
import main.data.request.RegistrationRequest;
import main.data.response.PasswordRecoveryResponse;
import main.data.response.RegistrationResponse;
import main.service.PasswordRecoveryServiceImpl;
import main.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/account")
public class ApiAccountController {
    private final PasswordRecoveryServiceImpl passwordRecoveryService;

    private final RegistrationService registrationService;

    @PutMapping(value = "/password/recovery")
    public ResponseEntity<PasswordRecoveryResponse> recovery(
            @RequestBody PasswordRecoveryRequest request) {
        return ResponseEntity.ok(passwordRecoveryService.restorePassword(request));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RegistrationResponse> registration(
            @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(registrationService.registrationNewPerson(request));
    }
}

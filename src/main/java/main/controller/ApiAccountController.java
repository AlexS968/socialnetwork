package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.PasswordRecoveryRequest;
import main.data.response.PasswordRecoveryResponse;
import main.service.PasswordRecoveryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/account")
public class ApiAccountController {
    private final PasswordRecoveryServiceImpl passwordRecoveryService;

    @PutMapping(value = "/password/recovery")
    public ResponseEntity<PasswordRecoveryResponse> recovery(
            @RequestBody PasswordRecoveryRequest request) {
        return ResponseEntity.ok(passwordRecoveryService.restorePassword(request));
    }
}

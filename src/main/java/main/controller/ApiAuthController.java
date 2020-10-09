package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.LoginRequest;
import main.data.response.LoginResponse;
import main.data.response.LogoutResponse;
import main.data.response.base.Response;
import main.data.response.type.ResponseMessage;
import main.data.response.type.PersonInLogin;
import main.service.PersonServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class ApiAuthController {
    private final PersonServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<Response<PersonInLogin>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Response<ResponseMessage>> logout() {
        return ResponseEntity.ok(userService.logout());
    }
}

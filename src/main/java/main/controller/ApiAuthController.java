package main.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import main.data.request.LoginRequest;
import main.data.response.CaptchaResponse;
import main.data.response.base.Response;
import main.data.response.type.ResponseMessage;
import main.data.response.type.PersonInLogin;
import main.exception.BadRequestException;
import main.exception.GlobalExceptionHandler;
import main.exception.apierror.ApiError;
import main.service.CaptchaServiceImpl;
import main.service.PersonServiceImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class ApiAuthController {
    private final PersonServiceImpl userService;
    private final CaptchaServiceImpl captchaService;

    @Value("${reCaptcha.secretCode}")
    public String secretCode;

    @Value("${reCaptcha.url}")
    public String captchaUrl;

    @PostMapping("/login")
    public ResponseEntity<Response<PersonInLogin>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Response<ResponseMessage>> logout() {
        return ResponseEntity.ok(userService.logout());
    }

    @PostMapping(value = "/captcha")
    public ResponseEntity<CaptchaResponse> confirmCaptcha(@RequestBody String token)  {
        return ResponseEntity.ok(captchaService.checkCaptcha(token, secretCode,captchaUrl));
    }
}
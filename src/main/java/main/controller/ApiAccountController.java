package main.controller;

import lombok.RequiredArgsConstructor;
import main.data.request.PasswordRecoveryRequest;
import main.data.request.PasswordSetRequest;
import main.data.request.RegistrationRequest;
import main.data.response.RegistrationResponse;
import main.data.response.base.Response;
import main.data.response.type.InfoInResponse;
import main.service.PasswordService;
import main.service.RegistrationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class ApiAccountController {

  private final PasswordService passwordService;
  private final RegistrationService registrationService;

  @Value("${linkToChange.password}")
  public String passwordChangeLink;
  @Value("${linkToChange.email}")
  public String emailChangeLink;

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

    String ipAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
        .getRequest().getRemoteAddr();

    System.out.println(ipAddress + "---------ipAddress");

    return ResponseEntity.ok(registrationService.registrationNewPerson(request, ipAddress));
  }
}

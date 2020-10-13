package main.controller;

import lombok.AllArgsConstructor;
import main.data.request.MeProfileRequest;
import main.data.response.InfoResponse;
import main.data.response.base.Response;
import main.data.response.type.InfoInResponse;
import main.data.response.type.MeProfile;
import main.data.response.type.MeProfileUpdate;
import main.service.PersonServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class PersonController {

  private final PersonServiceImpl personServiceImpl;

  @GetMapping("/me")
  public ResponseEntity<Response<MeProfile>> getCurrentUser() {

    return ResponseEntity.ok(personServiceImpl.getMe());
  }

  @PutMapping("/me")
  public ResponseEntity<Response<MeProfileUpdate>> updateCurrentUser(
      @RequestBody MeProfileRequest updatedCurrentUser) {

    return ResponseEntity.ok(personServiceImpl.putMe(updatedCurrentUser));

  }

  @DeleteMapping("/me")
  public ResponseEntity<Response<InfoInResponse> > delete() {
    return ResponseEntity.ok(personServiceImpl.deleteMe());
  }


}

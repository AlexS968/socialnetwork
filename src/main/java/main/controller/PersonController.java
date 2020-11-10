package main.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import main.data.request.MeProfileRequest;
import main.data.response.base.Response;
import main.data.response.type.InfoInResponse;
import main.data.response.type.MeProfile;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.service.PersonServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Api
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
    public ResponseEntity<Response<MeProfile>> updateCurrentUser(
            @RequestBody MeProfileRequest updatedCurrentUser) {

        return ResponseEntity.ok(personServiceImpl.putMe(updatedCurrentUser));

    }

    @DeleteMapping("/me")
    public ResponseEntity<Response<InfoInResponse>> delete() {
        return ResponseEntity.ok(personServiceImpl.deleteMe());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<MeProfile>> showPersonProfile(
            @PathVariable int id
    ) {
        return ResponseEntity.ok(personServiceImpl.getProfile(id));
    }

    @PutMapping("/block/{id}")
    public ResponseEntity blockUser(@PathVariable int id) {
        Response response = new Response();
        try {
            response = personServiceImpl.blockUser(id);
        } catch (BadRequestException ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Bad request"));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/block/{id}")
    public ResponseEntity unblockUser(@PathVariable int id) {
        Response response = new Response();
        try {
            response = personServiceImpl.unblockUser(id);
        } catch (BadRequestException ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Bad request"));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

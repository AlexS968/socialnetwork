package main.controller;

import lombok.AllArgsConstructor;
import main.data.response.FriendsResponse;
import main.exception.BadRequestException;
import main.exception.apierror.ApiError;
import main.service.FriendsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ApiFriendsController {

    private FriendsServiceImpl friendsService;

    @GetMapping(value = "/api/v1/friends")
    public ResponseEntity<FriendsResponse> friends(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int itemPerPage) {

        FriendsResponse response = new FriendsResponse();
        try {
            response = friendsService.getFriends(name, offset, itemPerPage);
        } catch (BadRequestException ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Bad request"));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping(value = "/api/v1/friends/{id}")
    public ResponseEntity removeFriend(@PathVariable int id) {
        FriendsResponse response = new FriendsResponse();
        try {
            response = friendsService.deleteFriend(id);
        } catch (BadRequestException ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Bad request"));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/api/v1/friends/recommendations")
    public ResponseEntity<FriendsResponse> recommendationFriends(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int itemPerPage) {

        FriendsResponse response = new FriendsResponse();
        System.out.println(1234);
        try {
            response = friendsService.getRecommendations(offset, itemPerPage);
        } catch (BadRequestException ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Bad request"));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping(value = "/api/v1/friends/{id}")
    public ResponseEntity addFriend(@PathVariable int id) {
        FriendsResponse response = new FriendsResponse();
        try {
            response = friendsService.addFriend(id);
        } catch (BadRequestException ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Bad request"));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/api/v1/friends/request")
    public ResponseEntity<FriendsResponse> getRequests(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int itemPerPage) {

        FriendsResponse response = new FriendsResponse();
        System.out.println(1);
        try {
            response = friendsService.getRequests(offset, itemPerPage);
        } catch (BadRequestException ex) {
            throw new BadRequestException(new ApiError("invalid_request", "Bad request"));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}

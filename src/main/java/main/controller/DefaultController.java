package main.controller;

import main.data.request.UserRequest;
import main.data.response.ProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

@Controller
public class DefaultController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.GET}, value = "/**/{path:[^\\.]*}")
    public String redirectToIndex() {
        return "forward:/";
    }

    //Заглушка логин
    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<ProfileResponse> auth(@RequestBody UserRequest userRequest){
        ProfileResponse response = new ProfileResponse(userRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Заглушка профиль
    @GetMapping("/api/v1/users/me")
    public ResponseEntity<ProfileResponse> me() {
        return new ResponseEntity<>(new ProfileResponse(), HttpStatus.OK);
    }

}

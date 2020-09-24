package main.controller;

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

//    //Заглушка профиль
//    @GetMapping("/api/v1/users/me")
//    public ResponseEntity<ProfileResponse> me() {
//        return new ResponseEntity<>(new ProfileResponse(), HttpStatus.OK);
//    }

}

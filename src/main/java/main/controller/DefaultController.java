package main.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Api
@Controller
public class DefaultController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/**/{path:[^\\.]*}")
    public String redirectToIndex() {
        return "forward:/";
    }
}

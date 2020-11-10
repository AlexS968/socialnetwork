package main.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
@Api
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
}

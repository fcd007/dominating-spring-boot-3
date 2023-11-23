package br.dev.dantas.point.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class EndPointController {

    @GetMapping("hi")
    public String hi() {
        return "OMAE WA MOU SHIDEIRU";
    }
}

package br.dev.dantas.point.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("api/v1/hello")
@Log4j2
public class EndPointController {

    @GetMapping("hi")
    public String hi() {
        return "OMAE WA MOU SHIDEIRU";
    }

    @PostMapping
    public Long save(@RequestBody String name) {
        log.info("Saveing name'{}", name);
        return ThreadLocalRandom.current().nextLong();
    }
}

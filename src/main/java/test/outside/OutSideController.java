package test.outside;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OutSideController {
    @GetMapping("test")
    public String test() {
        return "Testing outside academy.devdojo";
    }
}

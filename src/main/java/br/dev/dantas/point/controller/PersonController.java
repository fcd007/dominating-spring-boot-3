package br.dev.dantas.point.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = {"api/v1/customers","api/v1/customers/"})
public class PersonController {
    public static final List<String> NAMES =    List.of("Maria", "João", "Lucas", "Leticia", "Gabriela", "Laura");

    @GetMapping
    public List<String> list() {
        return NAMES;
    }

    @GetMapping("filter")
    public List<String> filter(@RequestParam(defaultValue = "") String name) {
        return NAMES.stream().filter(n -> n.equalsIgnoreCase(name)).toList();
    }

    @GetMapping("filterOptional")
    public List<String> filter(@RequestParam Optional<String> name) {
        return NAMES.stream().filter(n -> n.equalsIgnoreCase(name.orElse(""))).toList();
    }

    @GetMapping("filterList")
    public List<String> filter(@RequestParam List<String> names) {
        return NAMES.stream().filter(names::contains).toList();
    }

    @GetMapping("{name}")
    public String findByName(@PathVariable String name) {
        return NAMES.stream().filter(n -> n.equalsIgnoreCase(name))
                .findFirst()
                .orElseGet(() -> "");
    }
}

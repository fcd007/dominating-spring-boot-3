package br.dev.dantas.point.controller.personcontroller;

import br.dev.dantas.point.controller.animecontroller.IAnimeController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = {IPersonController.V1_PATH_DEFAULT, IPersonController.V1_PATH_OTHER})
public class PersonController {
    public static final List<String> NAMES = List.of("Maria", "João", "Lucas", "Leticia", "Gabriela", "Laura");

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

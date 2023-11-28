package br.dev.dantas.point.controller;

import br.dev.dantas.point.domain.Anime;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = {"api/v1/animes", "api/v1/animes/"})
public class AnimeController {

    @GetMapping
    public List<Anime> list(@RequestParam(required = false) String name) {
        var animes = Anime.gerlistAnimes();
        if(name == null) {
            return animes;
        }

        return animes.stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    @GetMapping("{id}")
    public Anime findById(@PathVariable Long id) {
        var animes = Anime.gerlistAnimes();
        return animes.stream().filter( anime -> anime.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}

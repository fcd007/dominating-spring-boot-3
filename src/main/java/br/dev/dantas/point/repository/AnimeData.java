package br.dev.dantas.point.repository;

import br.dev.dantas.point.domain.entity.Anime;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeData {

    private List<Anime> animes = new ArrayList<>();

    {
        var dragon = Anime.builder().id(1L).name("Dragon Ball Z").createdAt(LocalDateTime.now()).build();
        var yugioh = Anime.builder().id(2L).name("Yugi Oh").createdAt(LocalDateTime.now()).build();
        var samurai = Anime.builder().id(3L).name("Samurai X").createdAt(LocalDateTime.now()).build();

        animes.addAll(List.of(dragon, yugioh, samurai));
    }

    public List<Anime> getAnimes() {
        return animes;
    }
}

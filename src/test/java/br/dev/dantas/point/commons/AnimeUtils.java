package br.dev.dantas.point.commons;

import br.dev.dantas.point.domain.Anime;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeUtils {
    public List<Anime> newAnimeList() {
        var tom = Anime.builder().id(1L).name("Superman").createdAt(LocalDateTime.now()).build();
        var pokemon = Anime.builder().id(2L).name("Pokemon").createdAt(LocalDateTime.now()).build();
        var pink = Anime.builder().id(3L).name("Pink & Cerebro").createdAt(LocalDateTime.now()).build();

        return new ArrayList<>(List.of(tom, pokemon, pink));
    }

    public Anime newAnimeToSave() {
        return Anime.builder()
                .id(4L)
                .name("Liga da Justiça")
                .createdAt(LocalDateTime.now())
                .build();
    }
}

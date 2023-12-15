package br.dev.dantas.point.domain;


import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Anime {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private static List<Anime> animes = new ArrayList<>();

    static {
        var dragon = Anime.builder().id(1L).name("Dragon Ball Z").createdAt(LocalDateTime.now()).build();
        var yugioh = Anime.builder().id(2L).name("Yugi Oh").createdAt(LocalDateTime.now()).build();
        var samurai = Anime.builder().id(3L).name("Samurai X").createdAt(LocalDateTime.now()).build();
        var pokemon = Anime.builder().id(4L).name("Pokemon").createdAt(LocalDateTime.now()).build();
        var xmen = Anime.builder().id(5L).name("X-men").createdAt(LocalDateTime.now()).build();

        animes.addAll(List.of(dragon, yugioh, samurai, pokemon, xmen));
    }

    public static List<Anime> getAnimes() {
        return animes;
    }
}

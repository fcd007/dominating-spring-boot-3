package br.dev.dantas.point.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Anime {
    private Long id;
    private String name;
    private static List<Anime> animes = new ArrayList<>();

    static {
        var dragon = new Anime(1L, "Dragon Ball Z" );
        var yugi = new Anime(2L, "Yugi Oh" );
        var samurai = new Anime(3L, "Samurai X" );
        var pokemon = new Anime(4L, "Pokemon" );
        var xmen = new Anime(5L, "X-men Revolution" );

        animes.addAll(List.of(dragon, yugi, samurai, pokemon, xmen));
    }

    public static List<Anime> getAnimes() {
        return animes;
    }
}

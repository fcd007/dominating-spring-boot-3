package br.dev.dantas.point.domain;


import java.util.List;

public class Anime {
    private Long id;
    private String name;

    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static List<Anime> gerlistAnimes() {
        var dragon = new Anime(1L, "Dragon Ball Z" );
        var yugi = new Anime(2L, "Yugi Oh" );
        var samurai = new Anime(3L, "Samurai X" );
        var pokemon = new Anime(4L, "Pokemon" );
        var xmen = new Anime(5L, "X-men Revolution" );

        return List.of(dragon, yugi, samurai, pokemon, xmen);
    }
}

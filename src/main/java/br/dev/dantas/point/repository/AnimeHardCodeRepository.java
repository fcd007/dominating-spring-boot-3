package br.dev.dantas.point.repository;

import br.dev.dantas.point.domain.Anime;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AnimeHardCodeRepository {

    private static List<Anime> ANIMES = new ArrayList<>();

    static {
        var dragon = Anime.builder().id(1L).name("Dragon Ball Z").createdAt(LocalDateTime.now()).build();
        var yugioh = Anime.builder().id(2L).name("Yugi Oh").createdAt(LocalDateTime.now()).build();
        var samurai = Anime.builder().id(3L).name("Samurai X").createdAt(LocalDateTime.now()).build();

        ANIMES.addAll(List.of(dragon, yugioh, samurai));
    }

    public List<Anime> findAll() {
        return ANIMES;
    }

    public Optional<Anime> findById(Long id) {
        return ANIMES.stream().filter(anime -> anime.getId().equals(id)).findFirst();
    }

    public List<Anime> findByName(String name) {
        return name == null ? ANIMES : ANIMES.stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();
    }

    public Anime save(Anime anime) {
        ANIMES.add(anime);
        return anime;
    }

    public void delete(Anime anime) {
        ANIMES.remove(anime);
    }

    public void update(Anime anime) {
        delete(anime);
        save(anime);
    }
}

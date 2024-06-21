package br.dev.dantas.point.repository;

import br.dev.dantas.point.domain.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

    List<Anime> findByName(String name);
}

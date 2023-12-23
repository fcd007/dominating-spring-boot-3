package br.dev.dantas.point.service;

import br.dev.dantas.point.domain.Anime;
import br.dev.dantas.point.domain.Producer;
import br.dev.dantas.point.repository.AnimeHardCodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public class AnimeService {

    private AnimeHardCodeRepository animeHardCodeRepository;

    public AnimeService() {
        this.animeHardCodeRepository = new AnimeHardCodeRepository();
    }

    public List<Anime> listAll(String name) {
        return animeHardCodeRepository.findByName(name);
    }

    public Anime save(Anime anime) {
        return animeHardCodeRepository.save(anime);
    }

    public Anime findById(Long id) {
        return animeHardCodeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }

    public void delete(Long id) {
        var anime = findById(id);
        animeHardCodeRepository.delete(anime);
    }

    public void update(Anime animeToUpdate) {
        assertAnimeExists(animeToUpdate);
        animeHardCodeRepository.update(animeToUpdate);
    }

    private void assertAnimeExists(Anime animeToUpdate) {
        findById(animeToUpdate.getId());
    }
}
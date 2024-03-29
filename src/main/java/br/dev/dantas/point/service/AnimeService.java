package br.dev.dantas.point.service;

import br.dev.dantas.point.domain.entity.Anime;
import br.dev.dantas.point.repository.AnimeHardCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeHardCodeRepository animeHardCodeRepository;

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
        var user = findById(id);
        animeHardCodeRepository.delete(user);
    }

    public void update(Anime animeToUpdate) {
        assertAnimeExists(animeToUpdate);
        animeHardCodeRepository.update(animeToUpdate);
    }

    private void assertAnimeExists(Anime animeToUpdate) {
        findById(animeToUpdate.getId());
    }
}
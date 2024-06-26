package br.dev.dantas.point.service;

import br.dev.dantas.point.domain.entity.Anime;
import br.dev.dantas.point.repository.AnimeRepository;
import br.dev.dantas.point.utils.Constants;
import exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository repository;

    public List<Anime> findAll(String name) {
        return findByName(name);
    }

    public Page<Anime> listAnimes(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Anime> findByName(String name) {
        return name != null ? repository.findByName(name) : repository.findAll();
    }

    public Anime save(Anime anime) {
        return repository.save(anime);
    }

    public Anime findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(Constants.ANIME_NOT_FOUND));
    }

    public void delete(Long id) {
        var user = findById(id);
        repository.delete(user);
    }

    public void update(Anime animeToUpdate) {
        var user = findById(animeToUpdate.getId());
        repository.save(user);
    }
}
package br.dev.dantas.point.controller.animecontroller;

import br.dev.dantas.point.domain.Anime;
import br.dev.dantas.point.domain.Producer;
import br.dev.dantas.point.mappers.AnimeMapper;
import br.dev.dantas.point.request.AnimePostRequest;
import br.dev.dantas.point.request.AnimePutRequest;
import br.dev.dantas.point.request.ProducerPutRequest;
import br.dev.dantas.point.response.AnimeGetResponse;
import br.dev.dantas.point.response.AnimePostResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = {IAnimeController.V1_PATH_DEFAULT, IAnimeController.V1_PATH_OTHER})
@Log4j2
public class AnimeController{
    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> list(@RequestParam(required = false) String name) {
        log.info("Request received to list all animes, param name '{}'", name);
        var animes = Anime.getAnimes();
        var animeGetResponses = MAPPER.toAnimeGetResponseList(animes);
        if (name == null) {
            return ResponseEntity.ok(animeGetResponses);
        }

        animeGetResponses = animeGetResponses
                .stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .toList();
        return ResponseEntity.ok(animeGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.info("Request received find anime by id '{}'", id);
        var animeFound = Anime.getAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));

        var response = MAPPER.toAnimeGetResponse(animeFound);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest request) {
        log.info("Request create anime post method '{}'", request);
        var anime = MAPPER.toAnime(request);
        var response = MAPPER.toAnimePostResponse(anime);

        Anime.getAnimes().add(anime);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Request received to delete the anime by id'{}'", id);
        var animeFound = Anime.getAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found to be deleted"));
        Anime.getAnimes().remove(animeFound);
        return ResponseEntity.noContent().build();
    }


    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {
        log.info("Request received to update the anime '{}'", request);
        var animeToRemove = Anime.getAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found to be update"));

        var animeUpdated = MAPPER.toAnime(request, animeToRemove.getCreatedAt());

        Anime.getAnimes().remove(animeToRemove);
        Anime.getAnimes().add(animeUpdated);

        return ResponseEntity.noContent().build();
    }
}
package br.dev.dantas.point.controller.animecontroller;

import br.dev.dantas.point.domain.mappers.AnimeMapper;
import br.dev.dantas.point.controller.animecontroller.request.AnimePostRequest;
import br.dev.dantas.point.controller.animecontroller.request.AnimePutRequest;
import br.dev.dantas.point.controller.animecontroller.response.AnimeGetResponse;
import br.dev.dantas.point.controller.animecontroller.response.AnimePostResponse;
import br.dev.dantas.point.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {IAnimeController.V1_PATH_DEFAULT, IAnimeController.V1_PATH_OTHER})
@Log4j2
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeMapper mapper;

    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> list(@RequestParam(required = false) String name) {
        log.info("Request received to list all animes, param name '{}'", name);

        var anime = animeService.listAll(name);
        var animeGetResponses = mapper.toAnimeGetResponseList(anime);

        return ResponseEntity.ok(animeGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.info("Request received find anime by id '{}'", id);

        var anime = animeService.findById(id);
        var animeGetResponse = mapper.toAnimeGetResponse(anime);

        return ResponseEntity.ok(animeGetResponse);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest request) {
        log.info("Request create anime post method '{}'", request);

        var anime = mapper.toAnime(request);
        anime = animeService.save(anime);
        var response = mapper.toAnimePostResponse(anime);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Request received to delete the anime by id'{}'", id);

        animeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {
        log.info("Request received to update the anime '{}'", request);

        var animeToUpdate = mapper.toAnime(request);
        animeService.update(animeToUpdate);

        return ResponseEntity.noContent().build();
    }
}

package br.dev.dantas.point.controller.animecontroller;

import static br.dev.dantas.point.controller.animecontroller.IAnimeController.V1_PATH_DEFAULT;
import static br.dev.dantas.point.controller.animecontroller.IAnimeController.V1_PATH_OTHER;

import br.dev.dantas.point.controller.animecontroller.request.AnimePostRequest;
import br.dev.dantas.point.controller.animecontroller.request.AnimePutRequest;
import br.dev.dantas.point.controller.animecontroller.response.AnimeGetResponse;
import br.dev.dantas.point.controller.animecontroller.response.AnimePostResponse;
import br.dev.dantas.point.domain.mappers.AnimeMapper;
import br.dev.dantas.point.service.AnimeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {V1_PATH_DEFAULT, V1_PATH_OTHER})
@Log4j2
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class AnimeController implements IAnimeController {

  private final AnimeService animeService;
  private final AnimeMapper mapper;

  @GetMapping
  @Override
  public ResponseEntity<List<AnimeGetResponse>> findAllAnimes(@RequestParam(required = false) String name) {
    log.info("Request received to list all animes, param name '{}'", name);

    var anime = animeService.findAll(name);
    var animeGetResponses = mapper.toAnimeGetResponseList(anime);

    return ResponseEntity.ok(animeGetResponses);
  }

  @GetMapping(PAGINATED)
  @Override
  public ResponseEntity<Page<AnimeGetResponse>> findAllAnimesPageable(Pageable pageable) {
    log.info("Request received to list all animes paginated");

    var animeGetResponses = animeService.listAnimes(pageable).map(mapper::toAnimeGetResponse);

    return ResponseEntity.ok(animeGetResponses);
  }

  @GetMapping("{id}")
  @Override
  public ResponseEntity<AnimeGetResponse> findAnimeById(@PathVariable @Valid Long id) {
    log.info("Request received find anime by id '{}'", id);

    var anime = animeService.findById(id);
    var animeGetResponse = mapper.toAnimeGetResponse(anime);

    return ResponseEntity.ok(animeGetResponse);
  }

  @PostMapping
  @Override
  public ResponseEntity<AnimePostResponse> saveAnime(@RequestBody @Valid AnimePostRequest request) {
    log.info("Request create anime post method '{}'", request);

    var anime = mapper.toAnime(request);
    anime = animeService.save(anime);
    var response = mapper.toAnimePostResponse(anime);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @DeleteMapping("{id}")
  @Override
  public ResponseEntity<Void> deleteAnime(@PathVariable @Valid Long id) {
    log.info("Request received to delete the anime by id'{}'", id);

    animeService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping
  @Override
  public ResponseEntity<Void> updateAnime(@RequestBody @Valid AnimePutRequest request) {
    log.info("Request received to update the anime '{}'", request);

    var animeToUpdate = mapper.toAnime(request);
    animeService.update(animeToUpdate);

    return ResponseEntity.noContent().build();
  }
}

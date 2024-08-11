package br.dev.dantas.point.controller.animecontroller;

import br.dev.dantas.point.controller.animecontroller.request.AnimePostRequest;
import br.dev.dantas.point.controller.animecontroller.request.AnimePutRequest;
import br.dev.dantas.point.controller.animecontroller.response.AnimeGetResponse;
import br.dev.dantas.point.controller.animecontroller.response.AnimePostResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IAnimeController {

  String V1_PATH_DEFAULT = "/api/v1/animes";
  String V1_PATH_OTHER = "api/v1/animes/";

  /*** Metodos de conulta para paginacao de amimes*/
  static final String PAGINATED = "/paginated";

  @Operation(summary = "List all animes")
  ResponseEntity<List<AnimeGetResponse>> findAllAnimes(String name);

  @Operation(summary = "List Animes Paginated")
  ResponseEntity<Page<AnimeGetResponse>> findAllAnimesPageable(Pageable pageable);

  @Operation(summary = "Find Anime by Idd")
  ResponseEntity<AnimeGetResponse> findAnimeById(Long id);

  @Operation(summary = "Save Anime")
  ResponseEntity<AnimePostResponse> saveAnime(AnimePostRequest request);

  @Operation(summary = "Delete Anime")
  ResponseEntity<Void> deleteAnime(Long id);

  @Operation(summary = "Update Anime")
  ResponseEntity<Void> updateAnime(AnimePutRequest request);
}

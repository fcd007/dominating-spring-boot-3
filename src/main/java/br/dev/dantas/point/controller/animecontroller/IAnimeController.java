package br.dev.dantas.point.controller.animecontroller;

import br.dev.dantas.point.controller.animecontroller.request.AnimePostRequest;
import br.dev.dantas.point.controller.animecontroller.request.AnimePutRequest;
import br.dev.dantas.point.controller.animecontroller.response.AnimeGetResponse;
import br.dev.dantas.point.controller.animecontroller.response.AnimePostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
  ResponseEntity<List<AnimeGetResponse>> findAll(String name);

  @Operation(summary = "List Animes Paginated")
  ResponseEntity<Page<AnimeGetResponse>> findAll(Pageable pageable);

  @Operation(summary = "Find Anime by Idd")
  ResponseEntity<AnimeGetResponse> findById(Long id);

  @Operation(summary = "Save Anime")
  ResponseEntity<AnimePostResponse> save(AnimePostRequest request);

  @Operation(summary = "Delete Anime")
  ResponseEntity<Void> delete(Long id);

  @Operation(summary = "Update Anime")
  ResponseEntity<Void> update(AnimePutRequest request);
}

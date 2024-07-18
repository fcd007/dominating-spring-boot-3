package br.dev.dantas.point.controller.animecontroller;

import br.dev.dantas.point.controller.animecontroller.request.AnimePostRequest;
import br.dev.dantas.point.controller.animecontroller.request.AnimePutRequest;
import br.dev.dantas.point.controller.animecontroller.response.AnimeGetResponse;
import br.dev.dantas.point.controller.animecontroller.response.AnimePostResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IAnimeController {

  public static final String V1_PATH_DEFAULT = "/api/v1/animes";
  public static final String V1_PATH_OTHER = "api/v1/animes/";

  /*** Metodos de conulta para paginacao de amimes*/
  static final String PAGINATED = "/paginated";

  @Tag(name = "List Anime", description = "List Animes")
  ResponseEntity<List<AnimeGetResponse>> findAll(String name);

  @Tag(name = "List Anime Paginated", description = "List Animes Paginated")
  ResponseEntity<Page<AnimeGetResponse>> findAll(Pageable pageable);

  @Tag(name = "Find Anime by Id", description = "Find Anime by Id")
  ResponseEntity<AnimeGetResponse> findById(Long id);

  @Tag(name = "Save Anime", description = "Save Anime")
  ResponseEntity<AnimePostResponse> save(AnimePostRequest request);

  @Tag(name = "Delete Anime", description = "Delete Anime by id")
  ResponseEntity<Void> delete(Long id);

  @Tag(name = "Update Anime", description = "Update Anime by id")
  ResponseEntity<Void> update(AnimePutRequest request);
}

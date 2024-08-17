package br.dev.dantas.point.controller.animecontroller;

import static br.dev.dantas.point.controller.animecontroller.IAnimeController.PAGINATED;
import static br.dev.dantas.point.controller.animecontroller.IAnimeController.V1_PATH_DEFAULT;

import br.dev.dantas.point.commons.AnimeUtils;
import br.dev.dantas.point.commons.FileUtils;
import br.dev.dantas.point.config.SecurityConfig;
import br.dev.dantas.point.domain.entity.Anime;
import br.dev.dantas.point.domain.mappers.AnimeMapperImpl;
import br.dev.dantas.point.service.AnimeService;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

@WebMvcTest(AnimeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({AnimeMapperImpl.class, FileUtils.class, AnimeUtils.class, SecurityConfig.class, BCryptPasswordEncoder.class})
@WithMockUser
class AnimeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AnimeService animeService;

  @Autowired
  private FileUtils fileUtils;

  @Autowired
  private AnimeUtils animeUtils;

  @Test
  @DisplayName("findAll() returns a list with all Animes")
  @Order(1)
  void findAll_ReturnsAllAnimes_WhenSuccessful() throws Exception {
    var response = fileUtils.readResourceFile("anime/get-anime-null-name-200.json");

    BDDMockito.when(animeService.findAll(null)).thenReturn(animeUtils.newAnimeList());

    mockMvc.perform((MockMvcRequestBuilders
            .get(V1_PATH_DEFAULT)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("findAll() returns a page list with all Animes")
  @Order(2)
  void findAll_ReturnsAllAnimesPaginaded_WhenSuccessful() throws Exception {
    var response = fileUtils.readResourceFile("anime/get-anime-paginated-name-200.json");
    var animes = animeUtils.newAnimeList();
    var pageRequest = PageRequest.of(0, animes.size());
    PageImpl<Anime> pageAnime = new PageImpl<>(animes, pageRequest, 1);

    BDDMockito.when(animeService.listAnimes(
        BDDMockito.any(PageRequest.class)
    )).thenReturn(pageAnime);

    mockMvc.perform((MockMvcRequestBuilders
            .get(V1_PATH_DEFAULT + PAGINATED)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("findAll() returns a list with found animes when name is not null")
  @Order(2)
  void findAll_ReturnsFoundAnimes_WhenNamePassedAndFound() throws Exception {
    var response = fileUtils.readResourceFile("anime/get-anime-superman-name-200.json");
    var name = "Superman";

    BDDMockito.when(animeService.findAll(name))
        .thenReturn(Collections.singletonList(animeUtils.newAnimeList().get(0)));

    mockMvc.perform((MockMvcRequestBuilders
            .get(V1_PATH_DEFAULT))
            .param("name", name))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("findById() returns empty list when no anime is found")
  @Order(3)
  void findById_ReturnsAllAnimes_WhenSuccessful() throws Exception {
    var response = fileUtils.readResourceFile("anime/get-anime-by-id-200.json");
    var id = 1L;

    var animeFound = animeUtils.newAnimeList().stream().filter(anime -> anime.getId().equals(id))
        .findFirst().orElse(null);
    BDDMockito.when(animeService.findById(id)).thenReturn(animeFound);

    mockMvc.perform(MockMvcRequestBuilders.get(V1_PATH_DEFAULT + "/{id}", id))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));

  }

  @Test
  @DisplayName("findById() returns a throw NotFoundException not found")
  @Order(4)
  void findById_ThrowNotFoundException_WhenNoAnimeIsFound() throws Exception {
    var id = 10L;

    BDDMockito.when(animeService.findById(ArgumentMatchers.any()))
        .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

    mockMvc.perform(MockMvcRequestBuilders.get(V1_PATH_DEFAULT + "/{id}", id))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  @DisplayName("findByName() returns all animes when name is null")
  @Order(5)
  void findByName_ReturnsAllAnimes_WhenNameIsNulll() throws Exception {
    var response = fileUtils.readResourceFile("anime/get-anime-is-found-name-200.json");
    var animeNotFound = "animeTeste";

    BDDMockito.when(animeService.findByName(animeNotFound)).thenReturn(Collections.emptyList());

    mockMvc.perform((MockMvcRequestBuilders
            .get(V1_PATH_DEFAULT)).param("name", animeNotFound))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("save() create a Anime")
  @Order(6)
  void save_CreateAnime_WhenSuccessful() throws Exception {
    var request = fileUtils.readResourceFile("anime/post-request-anime-200.json");
    var response = fileUtils.readResourceFile("anime/post-response-anime-201.json");

    var userToBeSaved = animeUtils.newAnimeToSave();
    BDDMockito.when(animeService.save(ArgumentMatchers.any())).thenReturn(userToBeSaved);

    mockMvc.perform(MockMvcRequestBuilders.post(V1_PATH_DEFAULT).content(request)
            .contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
        .andExpect(
            MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("update() updates a anime")
  @Order(7)
  void update_UpdateAnime_WhenSuccessFul() throws Exception {
    var request = fileUtils.readResourceFile("anime/put-request-anime-204.json");

    BDDMockito.doNothing().when(animeService).update(ArgumentMatchers.any());

    mockMvc.perform(MockMvcRequestBuilders
            .put(V1_PATH_DEFAULT)
            .content(request).contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  @DisplayName("update() updates a throw NotFoundException not found")
  @Order(8)
  void update_ThrowNotFoundException_WhenNoAnimeIsFound() throws Exception {
    var request = fileUtils.readResourceFile("anime/put-request-anime-404.json");

    var animeToUpdated = animeUtils.newAnimeToSave();

    BDDMockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(animeService)
        .update(animeToUpdated);

    mockMvc.perform(MockMvcRequestBuilders.put(V1_PATH_DEFAULT).content(request)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  @DisplayName("delete() removes a anime")
  @Order(9)
  void delete_RemovesAnime_WhenSuccessFul() throws Exception {
    var id = 1L;
    mockMvc.perform(MockMvcRequestBuilders.delete(V1_PATH_DEFAULT + "/{id}", id))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  @DisplayName("delete() removes a throw NotFoundException not found to be delete")
  @Order(10)
  void delete_ThrowNotFoundException_WhenNoAnimeIsFound() throws Exception {
    var id = 10L;
    BDDMockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(animeService)
        .delete(id);

    mockMvc.perform(MockMvcRequestBuilders.delete(V1_PATH_DEFAULT + "/{id}", id))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @ParameterizedTest
  @MethodSource("postAnimeBadRequestSource")
  @DisplayName("save() returns bad request when fields are invalid")
  @Order(11)
  void save_ReturnsBadRequest_WhenFieldAreInvalid(String fileName, List<String> errors)
      throws Exception {
    var request = fileUtils.readResourceFile("anime/%s".formatted(fileName));

    var mvcResult = mockMvc.perform(
            MockMvcRequestBuilders
                .post(V1_PATH_DEFAULT)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();

    var resolvedException = mvcResult.getResolvedException();
    org.assertj.core.api.Assertions.assertThat(mvcResult.getResolvedException()).isNotNull();

    Assertions.assertThat(Objects.requireNonNull(resolvedException).getMessage()).contains(errors);

  }

  @ParameterizedTest
  @MethodSource("updateAnimeBadRequestSource")
  @DisplayName("update() returns bad request when fields are invalid")
  @Order(12)
  void update_ReturnsBadRequest_WhenFieldAreInvalid(String fileName, List<String> errors)
      throws Exception {
    var request = fileUtils.readResourceFile("anime/%s".formatted(fileName));

    var mvcResult = mockMvc.perform(
            MockMvcRequestBuilders
                .put(V1_PATH_DEFAULT)
                .content(request)
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();

    var resolvedException = mvcResult.getResolvedException();
    Assertions.assertThat(mvcResult.getResolvedException()).isNotNull();

    Assertions.assertThat(Objects.requireNonNull(resolvedException).getMessage()).contains(errors);
  }

  private static Stream<Arguments> postAnimeBadRequestSource() {

    var nameError = "the field name is required";

    var listErrors = List.of(nameError);

    return Stream.of(
        Arguments.of("post-request-anime-empty-fields-400.json", listErrors),
        Arguments.of("post-request-anime-blank-fields-400.json", listErrors)
    );
  }

  private static Stream<Arguments> updateAnimeBadRequestSource() {

    var nameError = "the field name is required";

    var listErrors = List.of(nameError);

    return Stream.of(
        Arguments.of("put-request-anime-empty-fields-400.json", listErrors),
        Arguments.of("put-request-anime-blank-fields-400.json", listErrors)
    );
  }
}
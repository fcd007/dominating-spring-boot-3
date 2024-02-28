package br.dev.dantas.point.controller.animecontroller;

import br.dev.dantas.point.commons.AnimeUtils;
import br.dev.dantas.point.commons.FileUtils;
import br.dev.dantas.point.domain.entity.Anime;
import br.dev.dantas.point.repository.AnimeData;
import br.dev.dantas.point.repository.AnimeHardCodeRepository;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(AnimeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimeData animeData;

    @SpyBean
    private AnimeHardCodeRepository repository;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private AnimeUtils animeUtils;

    @BeforeEach
    void init() {

        List<Anime> animes = animeUtils.newAnimeList();

        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
    }

    @Test
    @DisplayName("findAll() returns a list with all Animes")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenSuccessful() throws Exception {
        var response = fileUtils.readResourceFile("anime/get-anime-null-name-200.json");

        mockMvc.perform((MockMvcRequestBuilders
                .get(IAnimeController.V1_PATH_DEFAULT)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("findAll() returns a list with found animes when name is not null")
    @Order(2)
    void findAll_ReturnsFoundAnimes_WhenNamePassedAndFound() throws Exception {
        var response = fileUtils.readResourceFile("anime/get-anime-superman-name-200.json");
        var anime = "Superman";
        mockMvc.perform((MockMvcRequestBuilders
                .get(IAnimeController.V1_PATH_DEFAULT))
                .param("name", anime))
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
        mockMvc.perform(MockMvcRequestBuilders.get(IAnimeController.V1_PATH_DEFAULT + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("findById() returns a throw ResponseStatusException not found")
    @Order(4)
    void findById_ThrowResponseStatusException_WhenNoAnimeIsFound() throws Exception {
        var id = 10L;
        mockMvc.perform(MockMvcRequestBuilders.get(IAnimeController.V1_PATH_DEFAULT + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime not found"));
    }

    @Test
    @DisplayName("findByName() returns all animes when name is null")
    @Order(5)
    void findByName_ReturnsAllAnimes_WhenNameIsNulll() throws Exception {
        var response = fileUtils.readResourceFile("anime/get-anime-is-found-name-200.json");
        var animeNotFound = "animeTeste";

        mockMvc.perform((MockMvcRequestBuilders
                .get(IAnimeController.V1_PATH_DEFAULT)).param("name", animeNotFound))
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

        var animeToBeSaved = animeUtils.newAnimeToSave();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(animeToBeSaved);

        mockMvc.perform(MockMvcRequestBuilders
                .post(IAnimeController.V1_PATH_DEFAULT)
                .content(request).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("update() updates a anime")
    @Order(7)
    void update_UpdateAnime_WhenSuccessFul() throws Exception {
        var request = fileUtils.readResourceFile("anime/put-request-anime-204.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put(IAnimeController.V1_PATH_DEFAULT)
                        .content(request).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("update() updates a throw ResponseStatusException not found")
    @Order(8)
    void update_ThrowResponseStatusException_WhenNoAnimeIsFound() throws Exception {
        var request = fileUtils.readResourceFile("anime/put-request-anime-404.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put(IAnimeController.V1_PATH_DEFAULT)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime not found"));
    }

    @Test
    @DisplayName("delete() removes a anime")
    @Order(9)
    void delete_RemovesAnime_WhenSuccessFul() throws Exception {
        var id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete(IAnimeController.V1_PATH_DEFAULT + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("delete() removes a throw ResponseStatusException not found to be delete")
    @Order(10)
    void delete_ThrowResponseStatusException_WhenNoAnimeIsFound() throws Exception {
        var id = 11L;
        mockMvc.perform(MockMvcRequestBuilders.delete(IAnimeController.V1_PATH_DEFAULT + "/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime not found"));
    }
}
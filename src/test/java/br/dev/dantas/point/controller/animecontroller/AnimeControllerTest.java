package br.dev.dantas.point.controller.animecontroller;

import br.dev.dantas.point.domain.Anime;
import br.dev.dantas.point.repository.AnimeData;
import br.dev.dantas.point.repository.AnimeHardCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(AnimeController.class)
class AnimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimeData animeData;

    @SpyBean
    private AnimeHardCodeRepository repository;

    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    void init() {
        var tom = Anime.builder().id(1L).name("Tom & Jerry").createdAt(LocalDateTime.now()).build();
        var pokemon = Anime.builder().id(2L).name("Pokemon").createdAt(LocalDateTime.now()).build();
        var pink = Anime.builder().id(3L).name("Pink & Cerebro").createdAt(LocalDateTime.now()).build();

        List<Anime> animes = new ArrayList<>(List.of(tom, pokemon, pink));

        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
    }

    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }

    @Test
    @DisplayName("findAll() returns a list with all Animes")
    @Order(1)
    void findAll_ReturnsAllAnimes_WhenSuccessful() throws Exception {
        var response = readResourceFile("anime/get-anime-null-name-200.json");

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
        var response = readResourceFile("anime/get-anime-tom-e-jerry-name-200.json");
        var anime = "Tom & Jerry";
        mockMvc.perform((MockMvcRequestBuilders
                .get(IAnimeController.V1_PATH_DEFAULT))
                .param("name", anime))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("findByName() returns all animes when name is null")
    @Order(3)
    void findByName_ReturnsAllAnimes_WhenNameIsNulll() throws Exception {
        var response = readResourceFile("anime/get-anime-is-found-name-200.json");
        var animeNotFound = "animeTeste";

        mockMvc.perform((MockMvcRequestBuilders
                .get(IAnimeController.V1_PATH_DEFAULT)).param("name", animeNotFound))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("save() creates a producer")
    @Order(4)
    void save_CreateProducer_WhenSuccessful() throws Exception {
        var request = readResourceFile("anime/post-request-anime-200.json");
        var response = readResourceFile("anime/post-response-anime-201.json");

        var animeToBeSaved = Anime.builder().id(4L).name("Liga da Justiça").createdAt(LocalDateTime.now()).build();
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
    @Order(5)
    void update_UpdateAnime_WhenSuccessFul() throws Exception {
        var request = readResourceFile("anime/put-request-anime-204.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put(IAnimeController.V1_PATH_DEFAULT)
                        .content(request).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("update() updates a throw ResponseStatusException not found")
    @Order(6)
    void update_ThrowResponseStatusException_WhenNoAnimeIsFound() throws Exception {
        var request = readResourceFile("anime/put-request-anime-404.json");

        mockMvc.perform(MockMvcRequestBuilders
                        .put(IAnimeController.V1_PATH_DEFAULT)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                .isNotFound()).andExpect(MockMvcResultMatchers.status().reason("Anime not found"));
    }

    @Test
    @DisplayName("delete() removes a producer")
    @Order(7)
    void delete_RemovesAnime_WhenSuccessFul() throws Exception {
        var id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete(IAnimeController.V1_PATH_DEFAULT + "{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("delete() removes a throw ResponseStatusException not found")
    @Order(8)
    void delete_ThrowResponseStatusException_WhenNoAnimeIsFound() throws Exception{
        var id = 11L;
        mockMvc.perform(MockMvcRequestBuilders.delete(IAnimeController.V1_PATH_DEFAULT + "{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
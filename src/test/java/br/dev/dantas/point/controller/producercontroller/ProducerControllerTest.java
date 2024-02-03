package br.dev.dantas.point.controller.producercontroller;

import br.dev.dantas.point.domain.Producer;
import br.dev.dantas.point.repository.ProducerData;
import br.dev.dantas.point.repository.ProducerHardCodeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
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

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebMvcTest(ProducerController.class)
class ProducerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    private List<Producer> producers;

    @MockBean
    private ProducerData producerData;

    @SpyBean
    private ProducerHardCodeRepository repository;

    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    void init() {
        var universal = Producer.builder().id(1L).name("marvel").createdAt(LocalDateTime.now()).build();
        var luca = Producer.builder().id(2L).name("Luca").createdAt(LocalDateTime.now()).build();
        var marvel = Producer.builder().id(3L).name("universal").createdAt(LocalDateTime.now()).build();

        producers = new ArrayList<>(List.of(universal, luca, marvel));

        BDDMockito.when(producerData.getProducers()).thenReturn(producers);
    }

    @Test
    @DisplayName("findAll() returns a list with all producers")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenSuccessful() throws Exception {
        var response = readResourceFile("get-producer-null-name-200.json");
            mockMvc.perform(MockMvcRequestBuilders.get(IProducerController.V1_PATH_DEFAULT))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("findAll() returns a list with found producers when name is not null")
    @Order(2)
    void findAll_ReturnsFoundProducers_WhenNamePassedAndFound() throws Exception {
        var name = "marvel";

        var response = readResourceFile("get-producer-marvel-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(IProducerController.V1_PATH_DEFAULT).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("findBAll() returns an empty list when no producer is found by name")
    @Order(3)
    void findByAll_ReturnsEmptyList_WhenNoNameIsFound() throws Exception {
        var name = "x";

        var response = readResourceFile("get-producer-is-found-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get(IProducerController.V1_PATH_DEFAULT).param("name", name))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    private String readResourceFile(String fileName) throws Exception {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName)).getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }

    @Test
    @DisplayName("save() creates a producer")
    @Order(4)
    void save_CreateProducer_WhenSuccessful()  throws Exception {
        var request = readResourceFile("post-request-producer-200.json");
        var response = readResourceFile("post-response-producer-201.json");

        var producerToBeSaved = Producer.builder()
                .id(9L)
                .name("A24")
                .createdAt(LocalDateTime.now())
                .build();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(producerToBeSaved);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(IProducerController.V1_PATH_DEFAULT)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }
}

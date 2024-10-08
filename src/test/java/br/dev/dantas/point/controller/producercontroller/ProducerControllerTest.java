package br.dev.dantas.point.controller.producercontroller;

import static br.dev.dantas.point.controller.producercontroller.IProducerController.V1_PATH_DEFAULT;

import br.dev.dantas.point.commons.FileUtils;
import br.dev.dantas.point.commons.ProducerUtils;
import br.dev.dantas.point.config.SecurityConfig;
import br.dev.dantas.point.domain.mappers.ProducerMapperImpl;
import br.dev.dantas.point.service.ProducerService;
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
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

@WebMvcTest(ProducerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import({ProducerMapperImpl.class, FileUtils.class, ProducerUtils.class, SecurityConfig.class, BCryptPasswordEncoder.class})
@WithMockUser
class ProducerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private FileUtils fileUtils;

  @MockBean
  private ProducerService producerService;

  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  private ProducerUtils producerUtils;

  @Test
  @DisplayName("findAll() returns a list with all producers")
  @Order(1)
  void findAll_ReturnsAllProducers_WhenSuccessful() throws Exception {
    var response = fileUtils.readResourceFile("producer/get-producer-null-name-200.json");

    BDDMockito.when(producerService.findAll(null)).thenReturn(producerUtils.newProducerList());

    mockMvc.perform(MockMvcRequestBuilders.get(V1_PATH_DEFAULT))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("findAll() returns a list with found producers when name is not null")
  @Order(2)
  void findAll_ReturnsFoundProducers_WhenNamePassedAndFound() throws Exception {
    var response = fileUtils.readResourceFile("producer/get-producer-marvel-name-200.json");
    var name = "Marvel";

    BDDMockito.when(producerService.findAll(name))
        .thenReturn(Collections.singletonList(producerUtils.newProducerList().get(0)));

    mockMvc.perform(
            MockMvcRequestBuilders.get(V1_PATH_DEFAULT).param("name", name))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));

  }

  @Test
  @DisplayName("findAll() returns an empty list when no producer is found by name")
  @Order(3)
  void findByAll_ReturnsEmptyList_WhenNoNameIsFound() throws Exception {
    var response = fileUtils.readResourceFile("producer/get-producer-is-found-name-200.json");
    var name = "x";

    BDDMockito.when(producerService.findByName(name)).thenReturn(Collections.emptyList());

    mockMvc.perform(
            MockMvcRequestBuilders.get(V1_PATH_DEFAULT).param("name", name))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("save() creates a producer")
  @Order(4)
  void save_CreateProducer_WhenSuccessful() throws Exception {
    var request = fileUtils.readResourceFile("producer/post-request-producer-200.json");
    var response = fileUtils.readResourceFile("producer/post-response-producer-201.json");
    var producerToBeSaved = producerUtils.newProducerToSave();

    BDDMockito.when(producerService.save(ArgumentMatchers.any())).thenReturn(producerToBeSaved);

    mockMvc.perform(MockMvcRequestBuilders
            .post(V1_PATH_DEFAULT)
            .content(request)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(
            MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().json(response));
  }

  @Test
  @DisplayName("update() updates a producer")
  @Order(5)
  void update_UpdateProducer_WhenSuccessFul() throws Exception {
    var request = fileUtils.readResourceFile("producer/put-request-producer-204.json");

    BDDMockito.doNothing().when(producerService).update(ArgumentMatchers.any());

    mockMvc.perform(MockMvcRequestBuilders
            .put(V1_PATH_DEFAULT)
            .content(request)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  @DisplayName("update() updates a throw NotFoundException not found")
  @Order(6)
  void update_ThrowNotFoundException_WhenNoProducerIsFound() throws Exception {
    var request = fileUtils.readResourceFile("producer/put-request-producer-404.json");
    var producerToUpdated = producerUtils.newProducerToSave();

    BDDMockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(producerService)
        .update(producerToUpdated);

    mockMvc.perform(MockMvcRequestBuilders.put(V1_PATH_DEFAULT).content(request)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  @DisplayName("delete() removes a producer")
  @Order(7)
  void delete_RemovesProducer_WhenSuccessFul() throws Exception {
    var id = 1L;
    mockMvc.perform(
            MockMvcRequestBuilders.delete(V1_PATH_DEFAULT + "/{id}", id))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  @DisplayName("delete() removes a throw NotFoundException not found to be delete")
  @Order(8)
  void delete_ThrowNotFoundException_WhenNoProducerIsFound() throws Exception {
    var id = 11L;

    BDDMockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(producerService)
        .delete(id);

    mockMvc.perform(
            MockMvcRequestBuilders.delete(V1_PATH_DEFAULT + "/{id}", id))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andReturn();
  }

  @ParameterizedTest
  @MethodSource("postProducerBadRequestSource")
  @DisplayName("save() returns bad request when fields are invalid")
  @Order(9)
  void save_ReturnsBadRequest_WhenFieldAreInvalid(String fileName, List<String> errors)
      throws Exception {
    var request = fileUtils.readResourceFile("producer/%s".formatted(fileName));

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

    org.assertj.core.api.Assertions.assertThat(
        Objects.requireNonNull(resolvedException).getMessage()).contains(errors);

  }

  @ParameterizedTest
  @MethodSource("updateProducerBadRequestSource")
  @DisplayName("update() returns bad request when fields are invalid")
  @Order(9)
  void update_ReturnsBadRequest_WhenFieldAreInvalid(String fileName, List<String> errors)
      throws Exception {
    var request = fileUtils.readResourceFile("producer/%s".formatted(fileName));

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
    org.assertj.core.api.Assertions.assertThat(mvcResult.getResolvedException()).isNotNull();

    Assertions.assertThat(Objects.requireNonNull(resolvedException).getMessage()).contains(errors);
  }

  private static Stream<Arguments> postProducerBadRequestSource() {

    var nameError = "the field name is required";

    var listErrors = List.of(nameError);

    return Stream.of(
        Arguments.of("post-request-producer-empty-fields-400.json", listErrors),
        Arguments.of("post-request-producer-blank-fields-400.json", listErrors)
    );
  }

  private static Stream<Arguments> updateProducerBadRequestSource() {

    var nameError = "the field name is required";

    var listErrors = List.of(nameError);

    return Stream.of(
        Arguments.of("put-request-producer-empty-fields-400.json", listErrors),
        Arguments.of("put-request-producer-blank-fields-400.json", listErrors)
    );
  }
}
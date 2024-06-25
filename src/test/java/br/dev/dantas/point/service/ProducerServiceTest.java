package br.dev.dantas.point.service;

import br.dev.dantas.point.commons.ProducerUtils;
import br.dev.dantas.point.domain.entity.Producer;
import br.dev.dantas.point.repository.ProducerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService service;

    @Mock
    private ProducerRepository repository;

    private List<Producer> producers;

    @InjectMocks
    private ProducerUtils producerUtils;

    @BeforeEach
    void init() {
        producers = producerUtils.newProducerList();
    }

    @Test
    @DisplayName("findAll() returns a list with all producers")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(this.producers);

        service.findAll(null);
        Assertions.assertThat(producers).hasSameElementsAs(this.producers);
    }

    @Test
    @DisplayName("findAll() returns a list with found producers when name is not null")
    @Order(2)
    void findAll_ReturnsFoundProducers_WhenNamePassedAndFound() {
        var name = "Marvel";

        List<Producer> producersFound = this.producers.stream().filter(producer -> producer.getName().equals(name)).toList();
        BDDMockito.when(repository.findByName(name)).thenReturn(producersFound);
        var listProducers = service.findAll(name);

        Assertions.assertThat(listProducers).hasSize(1).contains(producersFound.get(0));
    }

    @Test
    @DisplayName("findAll() returns an empty list when no producer is found by name")
    @Order(3)
    void findByAll_ReturnsEmptyList_WhenNoNameIsFound() {
        var name = "x";

        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());
        var producerList = service.findAll(name);
        Assertions.assertThat(producerList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById() returns a optional producer is id exists")
    @Order(4)
    void findById_ReturnsOptionalProducer_WhenIsIdExists() {
        var id = 1L;

        var producerExpected = this.producers.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(producerExpected));

        var producerOptional = service.findById(id);
        Assertions.assertThat(producerOptional).isEqualTo(producerExpected);
    }

    @Test
    @DisplayName("findById() returns a optional producer is does id not exists")
    @Order(5)
    void findById_ReturnsEmptyOptionalProducer_WhenIsDoesIdNotExists() {
        var id = 1L;

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThatException()
                .isThrownBy(() -> service.findById(id))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("save() creates a producer")
    @Order(6)
    void save_CreateProducer_WhenSuccessful() {
        var producerToBeSaved = producerUtils.newProducerToSave();

        BDDMockito.when(repository.save(producerToBeSaved)).thenReturn(producerToBeSaved);
        var producer = service.save(producerToBeSaved);

        Assertions.assertThat(producer).isEqualTo(producerToBeSaved).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete() removes a producer")
    @Order(7)
    void delete_RemovesProducer_WhenSuccessFul() {
        var id = 1L;

        var producerToDelete = this.producers.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(producerToDelete));
        BDDMockito.doNothing().when(repository).delete(producerToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(id));
    }

    @Test
    @DisplayName("delete() removes a throw ResponseStatusException not found")
    @Order(8)
    void delete_ThrowResponseStatusException_WhenNoProducerIsFound() {
        var id = 1L;

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(id))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("update() updates a producer")
    @Order(9)
    void update_UpdateProducer_WhenSuccessFul() {
        var id = 1L;

        var producerToUpdate = this.producers.get(0);
        producerToUpdate.setName("Image");

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(producerToUpdate));
        BDDMockito.when(repository.save(producerToUpdate)).thenReturn(producerToUpdate);
        service.update(producerToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(producerToUpdate));
    }

    @Test
    @DisplayName("update() updates a throw ResponseStatusException not found")
    @Order(10)
    void update_ThrowResponseStatusException_WhenNoProducerIsFound() {
        var id = 1L;

        var producerToUpdate = this.producers.get(0);
        producerToUpdate.setName("Kialo");
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(producerToUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }
}
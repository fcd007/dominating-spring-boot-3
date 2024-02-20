package br.dev.dantas.point.repository;

import br.dev.dantas.point.commons.ProducerUtils;
import br.dev.dantas.point.domain.entity.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerHardCodeRepositoryTest {
    @InjectMocks
    private ProducerHardCodeRepository repository;

    @Mock
    private ProducerData producerData;

    private List<Producer> producers;

    @InjectMocks
    private ProducerUtils producerUtils;

    @BeforeEach
    void init() {

        producers = producerUtils.newProducerList();

        BDDMockito.when(producerData.getProducers()).thenReturn(producers);
    }

    @Test
    @DisplayName("findAll() returns a list with all producers")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenSuccessful() {
        var producers = repository.findAll();
        Assertions.assertThat(producers).hasSameElementsAs(producers);
    }

    @Test
    @DisplayName("findById() returns an object with given id")
    @Order(2)
    void findById_ReturnsAllProducer_WhenSuccessful() {
        var producerOptional = repository.findById(3L);
        Assertions.assertThat(producerOptional).isPresent().contains(producers.get(2));
    }

    @Test
    @DisplayName("findByName() returns all producers when name is null")
    @Order(3)
    void findByName_ReturnsAllProducers_WhenNameIsNulll() {
        var producers = repository.findByName(null);
        Assertions.assertThat(producers).hasSameElementsAs(producers);
    }

    @Test
    @DisplayName("findByName() returns list witg filtered producers name is not null")
    @Order(4)
    void findByName_ReturnsFilteredProducers_WhenNameIsNotNulll() {
        var producers = repository.findByName("Luca");
        Assertions.assertThat(producers).hasSize(1).contains(this.producers.get(1));
    }

    @Test
    @DisplayName("findByName() returns empty list when no producer is found")
    @Order(5)
    void findByName_ReturnsEmptyListOfProducers_WhenNameIsNotNulll() {
        var producers = repository.findByName("Abasd");
        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save() creates a producer")
    @Order(6)
    void save_CreatesProducer_WhenSuccessFul() {
        var producerToBeSaved = producerUtils.newProducerToSave();

        var producer = repository.save(producerToBeSaved);

        Assertions.assertThat(producer)
                .isEqualTo(producerToBeSaved)
                .hasNoNullFieldsOrProperties();

        var producers = repository.findAll();

        Assertions.assertThat(producers).contains(producerToBeSaved);
    }

    @Test
    @DisplayName("delete() removes a producer")
    @Order(7)
    void delete_RemovesProducer_WhenSuccessFul() {
        var producerToDelete = this.producers.get(0);
        repository.delete(producerToDelete);

        Assertions.assertThat(this.producers).doesNotContain(producerToDelete);
    }

    @Test
    @DisplayName("update() updates a producer")
    @Order(8)
    void update_UpdateProducer_WhenSuccessFul() {
        var producerToUpdate = this.producers.get(0);

        producerToUpdate.setName("Image");

        repository.update(producerToUpdate);
        Assertions.assertThat(this.producers).contains(producerToUpdate);
        this.producers.stream().filter(producer -> producer.getId().equals(producerToUpdate.getId())).findFirst().ifPresent(producer -> Assertions.assertThat(producer.getName()).isEqualTo(producerToUpdate.getName()));
    }
}
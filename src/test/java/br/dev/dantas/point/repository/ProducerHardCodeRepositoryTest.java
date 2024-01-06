package br.dev.dantas.point.repository;

import br.dev.dantas.point.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProducerHardCodeRepositoryTest {
    @InjectMocks
    private ProducerHardCodeRepository repository;

    @Mock
    private ProducerData producerData;

    private List<Producer> producers;

    @BeforeEach
    void init() {
        var universal = Producer.builder().id(1L).name("universal").createdAt(LocalDateTime.now()).build();
        var luca = Producer.builder().id(2L).name("Luca").createdAt(LocalDateTime.now()).build();
        var marvel = Producer.builder().id(3L).name("marvel").createdAt(LocalDateTime.now()).build();

        producers = new ArrayList<>(List.of(universal, luca, marvel));

        BDDMockito.when(producerData.getProducers()).thenReturn(producers);
    }

    @Test
    @DisplayName("findAll() returns a list with all producers")
    void findAll_ReturnsAllProducers_WhenSuccessful() {
        var producers = repository.findAll();
        Assertions.assertThat(producers).hasSameElementsAs(producers);
    }

    @Test
    @DisplayName("findById() returns an object with given id")
    void findById_ReturnsAllProducer_WhenSuccessful() {
        var producerOptional = repository.findById(3L);
        Assertions.assertThat(producerOptional).isPresent().contains(producers.get(2));
    }

    @Test
    @DisplayName("findByName() returns all producers when name is null")
    void findByName_ReturnsAllProducers_WhenNameIsNulll() {
        var producers = repository.findByName(null);
        Assertions.assertThat(producers).hasSameElementsAs(producers);
    }

    @Test
    @DisplayName("findByName() returns list witg filtered producers name is not null")
    void findByName_ReturnsFilteredProducers_WhenNameIsNotNulll() {
        var producers = repository.findByName("Luca");
        Assertions.assertThat(producers).hasSize(1).contains(this.producers.get(1));
    }

    @Test
    @DisplayName("findByName() returns empty list when no producer is found")
    void findByName_ReturnsEmptyListOfProducers_WhenNameIsNotNulll() {
        var producers = repository.findByName("Abasd");
        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save() creates a producer")
    void save_CreatesProducer_WhenSuccessFul() {
        var producerToBeSaved = Producer.builder()
                .id(6L)
                .name("Universal")
                .createdAt(LocalDateTime.now())
                .build();
        var producer = repository.save(producerToBeSaved);
        Assertions.assertThat(producer)
                .isEqualTo(producerToBeSaved).hasNoNullFieldsOrProperties();

        var producers = repository.findAll();
        Assertions.assertThat(producers).contains(producerToBeSaved);
    }

    @Test
    @DisplayName("delete() removes a producer")
    void delete_RemovesProducer_WhenSuccessFul() {
        var producerToDelete = this.producers.get(0);
        repository.delete(producerToDelete);

        Assertions.assertThat(this.producers).doesNotContain(producerToDelete);
    }

    @Test
    @DisplayName("update() updates a producer")
    void update_UpdateProducer_WhenSuccessFul() {
        var producerToUpdate = this.producers.get(0);

        producerToUpdate.setName("Image");

        repository.update(producerToUpdate);
        Assertions.assertThat(this.producers).contains(producerToUpdate);
        this.producers.stream().filter(producer -> producer.getId().equals(producerToUpdate.getId()))
                .findFirst()
                .ifPresent(producer -> Assertions.assertThat(producer.getName()).isEqualTo(producerToUpdate.getName()));
    }
}
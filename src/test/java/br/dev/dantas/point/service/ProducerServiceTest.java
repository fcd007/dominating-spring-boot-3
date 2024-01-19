package br.dev.dantas.point.service;

import br.dev.dantas.point.domain.Producer;
import br.dev.dantas.point.repository.ProducerHardCodeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService service;

    @Mock
    private ProducerHardCodeRepository repository;

    private List<Producer> producers;

    @BeforeEach
    void init() {
        var universal = Producer.builder().id(1L).name("universal").createdAt(LocalDateTime.now()).build();
        var luca = Producer.builder().id(2L).name("Luca").createdAt(LocalDateTime.now()).build();
        var marvel = Producer.builder().id(3L).name("marvel").createdAt(LocalDateTime.now()).build();

        producers = new ArrayList<>(List.of(universal, luca, marvel));
    }

    @Test
    @DisplayName("findAll() returns a list with all producers")
    @Order(1)
    void findAll_ReturnsAllProducers_WhenSuccessful() {
        BDDMockito.when(repository.findByName(null)).thenReturn(this.producers);

        var producers = service.listAll(null);
        Assertions.assertThat(producers).hasSameElementsAs(null);
    }

    @Test
    @DisplayName("findAll() returns a list with found producers when name is not null")
    @Order(2)
    void findAll_ReturnsFoundProducers_WhenNamePassedAndFound() {
        var name = "universal";
        List<Producer> producersFound = this.producers.stream().filter(producer -> producer.getName().equals(name)).toList();

        BDDMockito.when(repository.findByName(name)).thenReturn(producersFound);

        var producers = service.listAll(name);
        Assertions.assertThat(producers).hasSize(1).contains(producersFound.get(0));
    }

    @Test
    @DisplayName("findById() returns a optional producer is id exists")
    @Order(3)
    void  findById_ReturnsOptionalProducer_WhenIsIdExists() {
        var id = 1L;
        var producerExpected = this.producers.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(producerExpected));

        var producerOptional = service.findById(id);
        Assertions.assertThat(producerOptional).isPresent().contains(producerExpected);
    }
}
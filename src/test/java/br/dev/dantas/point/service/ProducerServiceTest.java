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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void findAll_ReturnsAllProducers_WhenSuccessful() {
        BDDMockito.when(repository.findByName(null)).thenReturn(this.producers);

        var producers = service.listAll(null);
        org.assertj.core.api.Assertions.assertThat(producers).hasSameElementsAs(producers);
    }

}
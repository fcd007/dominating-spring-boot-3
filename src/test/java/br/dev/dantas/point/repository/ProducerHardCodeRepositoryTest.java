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

        producers = List.of(universal, luca, marvel);

        BDDMockito.when(producerData.getProducers()).thenReturn(producers);
    }

    @Test
    @DisplayName("findAll returns a list with all producers")
    void findAll_ReturnsAllProducers_WhenSuccessful() {
        var producers = repository.findAll();
        Assertions.assertThat(producers).hasSize(3);
    }
}
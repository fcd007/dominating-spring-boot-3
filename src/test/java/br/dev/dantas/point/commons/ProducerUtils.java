package br.dev.dantas.point.commons;

import br.dev.dantas.point.domain.entity.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProducerUtils {
    public List<Producer> newProducerList() {
        var universal = Producer.builder().id(1L).name("Marvel").createdAt(LocalDateTime.now()).lastUpdatedOn(LocalDateTime.now()).build();
        var luca = Producer.builder().id(2L).name("Luca").createdAt(LocalDateTime.now()).lastUpdatedOn(LocalDateTime.now()).build();
        var marvel = Producer.builder().id(3L).name("Universal").createdAt(LocalDateTime.now()).lastUpdatedOn(LocalDateTime.now()).build();

        return new ArrayList<>(List.of(universal, luca, marvel));
    }

    public Producer newProducerToSave() {
        return Producer.builder()
                .id(9L)
                .name("A24")
                .createdAt(LocalDateTime.now())
                .lastUpdatedOn(LocalDateTime.now())
                .build();
    }
}

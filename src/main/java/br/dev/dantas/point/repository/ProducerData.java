package br.dev.dantas.point.repository;

import br.dev.dantas.point.domain.entity.Producer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProducerData {

    private List<Producer> producers = new ArrayList<>();

    {
        var mappa = Producer.builder().id(1L).name("MAPPA").createdAt(LocalDateTime.now()).build();
        var kyoto = Producer.builder().id(2L).name("Kyoto Animation").createdAt(LocalDateTime.now()).build();
        var madhouse = Producer.builder().id(3L).name("Madhouse").createdAt(LocalDateTime.now()).build();

        producers.addAll(List.of(mappa, kyoto, madhouse));
    }

    public List<Producer> getProducers() {
        return producers;
    }
}

package br.dev.dantas.point.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class Producer {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private static List<Producer> producers = new ArrayList<>();

    static {
        var mappa = Producer.builder().id(1L).name("MAPPA").createdAt(LocalDateTime.now()).build();
        var kyoto = Producer.builder().id(2L).name("Kyoto Animation").createdAt(LocalDateTime.now()).build();
        var madhouse = Producer.builder().id(3L).name("Madhouse").createdAt(LocalDateTime.now()).build();

        producers.addAll(List.of(mappa, kyoto, madhouse));
    }

    public static List<Producer> getProducers() {
        return producers;
    }
}
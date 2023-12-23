package br.dev.dantas.point.domain;


import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Anime {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private static List<Anime> animes = new ArrayList<>();
}
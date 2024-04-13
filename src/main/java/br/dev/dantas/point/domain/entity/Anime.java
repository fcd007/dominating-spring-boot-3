package br.dev.dantas.point.domain.entity;


import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Anime {

    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private static List<Anime> animes = new ArrayList<>();
}
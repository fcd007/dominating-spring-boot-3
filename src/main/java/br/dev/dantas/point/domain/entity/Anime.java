package br.dev.dantas.point.domain.entity;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Anime {

    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private static List<Anime> animes = new ArrayList<>();
}
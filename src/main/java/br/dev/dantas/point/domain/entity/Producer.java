package br.dev.dantas.point.domain.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producer {

    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private static List<Producer> producers = new ArrayList<>();
}
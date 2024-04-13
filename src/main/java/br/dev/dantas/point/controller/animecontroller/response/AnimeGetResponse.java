package br.dev.dantas.point.controller.animecontroller.response;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class AnimeGetResponse {
    private Long id;
    private String name;
}
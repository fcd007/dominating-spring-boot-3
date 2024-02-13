package br.dev.dantas.point.controller.animecontroller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AnimePutResponse {
    private Long id;
    private String name;
}
package br.dev.dantas.point.controller.producercontroller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ProducerPostResponse {
    private Long id;
    private String name;
}
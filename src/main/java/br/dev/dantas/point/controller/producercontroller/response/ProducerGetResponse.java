package br.dev.dantas.point.controller.producercontroller.response;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class ProducerGetResponse {
    private Long id;
    private String name;
}
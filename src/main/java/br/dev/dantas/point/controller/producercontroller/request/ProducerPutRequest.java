package br.dev.dantas.point.controller.producercontroller.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class ProducerPutRequest {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
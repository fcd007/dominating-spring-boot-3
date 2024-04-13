package br.dev.dantas.point.controller.producercontroller.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class ProducerPostResponse {

    @NotNull
    private Long id;

    @NotBlank(message = "the field name is required")
    private String name;
}
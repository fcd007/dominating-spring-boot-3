package br.dev.dantas.point.controller.producercontroller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
public class ProducerPostRequest {

    @NotBlank(message = "the field name is required")
    private String name;
}

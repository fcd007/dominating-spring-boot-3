package br.dev.dantas.point.controller.producercontroller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class ProducerPutRequest {

    @NotNull
    private Long id;

    @NotBlank(message = "the field name is required")
    private String name;

    private LocalDateTime createdAt;
}
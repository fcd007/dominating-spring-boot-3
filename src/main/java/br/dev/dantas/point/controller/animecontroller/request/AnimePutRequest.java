package br.dev.dantas.point.controller.animecontroller.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class AnimePutRequest {

    @NotNull
    private Long id;

    @NotBlank(message = "the field name is required")
    private String name;

    private LocalDateTime createdAt;
}
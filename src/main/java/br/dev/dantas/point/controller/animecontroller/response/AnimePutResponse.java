package br.dev.dantas.point.controller.animecontroller.response;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class AnimePutResponse {

    @NotNull
    private Long id;

    @NotBlank(message = "the field name is required")
    private String name;
}
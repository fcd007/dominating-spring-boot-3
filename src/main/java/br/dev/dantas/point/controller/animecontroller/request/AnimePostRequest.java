package br.dev.dantas.point.controller.animecontroller.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
public class AnimePostRequest {

    @NotBlank(message = "the field name is required")
    private String name;
}

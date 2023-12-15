package br.dev.dantas.point.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class AnimePutRequest {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
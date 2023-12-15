package br.dev.dantas.point.mappers;

import br.dev.dantas.point.domain.Anime;
import br.dev.dantas.point.request.AnimePostRequest;
import br.dev.dantas.point.request.AnimePutRequest;
import br.dev.dantas.point.response.AnimeGetResponse;
import br.dev.dantas.point.response.AnimePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AnimeMapper {
    AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Anime toAnime(AnimePostRequest request);

    @Mapping(source = "createdAt", target = "createdAt")
    Anime toAnime(AnimePutRequest request, LocalDateTime createdAt);

    AnimePostResponse toAnimePostResponse(Anime anime);

    AnimeGetResponse toAnimeGetResponse(Anime anime);

    List<AnimeGetResponse> toAnimeGetResponseList(List<Anime> animes);
}

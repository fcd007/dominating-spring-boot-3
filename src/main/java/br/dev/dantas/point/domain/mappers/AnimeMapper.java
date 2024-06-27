package br.dev.dantas.point.domain.mappers;

import br.dev.dantas.point.controller.animecontroller.request.AnimePostRequest;
import br.dev.dantas.point.controller.animecontroller.request.AnimePutRequest;
import br.dev.dantas.point.controller.animecontroller.response.AnimeGetResponse;
import br.dev.dantas.point.controller.animecontroller.response.AnimePostResponse;
import br.dev.dantas.point.controller.animecontroller.response.AnimePutResponse;
import br.dev.dantas.point.domain.entity.Anime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnimeMapper {

  Anime toAnime(AnimePostRequest request);

  Anime toAnime(AnimePutRequest request);

  AnimePostResponse toAnimePostResponse(Anime anime);

  AnimeGetResponse toAnimeGetResponse(Anime anime);

  AnimePutResponse toAnimePutResponse(Anime anime);

  List<AnimeGetResponse> toAnimeGetResponseList(List<Anime> animes);
}

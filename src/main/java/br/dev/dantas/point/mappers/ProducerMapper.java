package br.dev.dantas.point.mappers;

import br.dev.dantas.point.domain.Anime;
import br.dev.dantas.point.domain.Producer;
import br.dev.dantas.point.request.ProducerPostRequest;
import br.dev.dantas.point.request.ProducerPutRequest;
import br.dev.dantas.point.response.ProducerGetResponse;
import br.dev.dantas.point.response.ProducerPostResponse;
import br.dev.dantas.point.response.ProducerPutResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ProducerMapper {

    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Producer toProducer(ProducerPostRequest request);

    Producer toProducer(ProducerPutRequest request);

    ProducerPostResponse toProducerPostResponse(Producer producer);
    ProducerGetResponse toProducerGetResponse(Producer producer);
    ProducerPutResponse toProducerPutResponse(Producer producer);
    List<ProducerGetResponse> toProducerGetResponseList(List<Producer> producers);
}

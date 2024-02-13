package br.dev.dantas.point.domain.mappers;

import br.dev.dantas.point.domain.entity.Producer;
import br.dev.dantas.point.controller.producercontroller.request.ProducerPostRequest;
import br.dev.dantas.point.controller.producercontroller.request.ProducerPutRequest;
import br.dev.dantas.point.controller.producercontroller.response.ProducerGetResponse;
import br.dev.dantas.point.controller.producercontroller.response.ProducerPostResponse;
import br.dev.dantas.point.controller.producercontroller.response.ProducerPutResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProducerMapper {
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Producer toProducer(ProducerPostRequest request);

    Producer toProducer(ProducerPutRequest request);

    ProducerPostResponse toProducerPostResponse(Producer producer);

    ProducerGetResponse toProducerGetResponse(Producer producer);

    ProducerGetResponse toProducerGetResponse(Optional<Producer> producer);

    ProducerPutResponse toProducerPutResponse(Producer producer);

    List<ProducerGetResponse> toProducerGetResponseList(List<Producer> producers);
}

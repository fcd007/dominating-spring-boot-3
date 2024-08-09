package br.dev.dantas.point.domain.mappers;

import br.dev.dantas.point.annotation.EncodedMapping;
import br.dev.dantas.point.controller.producercontroller.request.ProducerPostRequest;
import br.dev.dantas.point.controller.producercontroller.request.ProducerPutRequest;
import br.dev.dantas.point.controller.producercontroller.response.ProducerGetResponse;
import br.dev.dantas.point.controller.producercontroller.response.ProducerPostResponse;
import br.dev.dantas.point.controller.producercontroller.response.ProducerPutResponse;
import br.dev.dantas.point.domain.entity.Producer;
import java.util.List;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProducerMapper {

  Producer toProducer(ProducerPostRequest request);

  Producer toProducer(ProducerPutRequest request);

  ProducerPostResponse toProducerPostResponse(Producer producer);

  ProducerGetResponse toProducerGetResponse(Producer producer);

  ProducerGetResponse toProducerGetResponse(Optional<Producer> producer);

  ProducerPutResponse toProducerPutResponse(Producer producer);

  List<ProducerGetResponse> toProducerGetResponseList(List<Producer> producers);
}

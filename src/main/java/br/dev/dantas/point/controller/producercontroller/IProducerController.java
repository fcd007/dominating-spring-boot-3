package br.dev.dantas.point.controller.producercontroller;

import br.dev.dantas.point.controller.producercontroller.request.ProducerPostRequest;
import br.dev.dantas.point.controller.producercontroller.request.ProducerPutRequest;
import br.dev.dantas.point.controller.producercontroller.response.ProducerGetResponse;
import br.dev.dantas.point.controller.producercontroller.response.ProducerPostResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface IProducerController {

  public static final String V1_PATH_DEFAULT = "/api/v1/producers";
  public static final String V1_PATH_OTHER = "/api/v1/producers/";

  @Tag(name = "List producers", description = "List all producers")
  ResponseEntity<List<ProducerGetResponse>> list(String name);

  @Tag(name = "Find producer", description = "Find producer by id")
  ResponseEntity<ProducerGetResponse> findById(Long id);

  @Tag(name = "Save producer", description = "Save producer")
  ResponseEntity<ProducerPostResponse> save(ProducerPostRequest request);

  @Tag(name = "Delete producer", description = "Delete producer by id")
  ResponseEntity<Void> deleteById(Long id);

  @Tag(name = "Update producer", description = "Update producer by id")
  ResponseEntity<Void> update(ProducerPutRequest request);
}

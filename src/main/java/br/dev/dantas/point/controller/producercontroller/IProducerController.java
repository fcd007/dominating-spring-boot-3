package br.dev.dantas.point.controller.producercontroller;

import br.dev.dantas.point.controller.producercontroller.request.ProducerPostRequest;
import br.dev.dantas.point.controller.producercontroller.request.ProducerPutRequest;
import br.dev.dantas.point.controller.producercontroller.response.ProducerGetResponse;
import br.dev.dantas.point.controller.producercontroller.response.ProducerPostResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface IProducerController {

  public static final String V1_PATH_DEFAULT = "/api/v1/producers";
  public static final String V1_PATH_OTHER = "/api/v1/producers/";

  @Operation(summary = "List producers")
  ResponseEntity<List<ProducerGetResponse>> list(String name);

  @Operation(summary = "Find producer")
  ResponseEntity<ProducerGetResponse> findById(Long id);

  @Operation(summary = "Save producer")
  ResponseEntity<ProducerPostResponse> save(ProducerPostRequest request);

  @Operation(summary = "Delete producer")
  ResponseEntity<Void> deleteById(Long id);

  @Operation(summary = "Update producer")
  ResponseEntity<Void> update(ProducerPutRequest request);
}

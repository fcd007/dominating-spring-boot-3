package br.dev.dantas.point.controller.producercontroller;

import static br.dev.dantas.point.controller.producercontroller.IProducerController.V1_PATH_DEFAULT;

import br.dev.dantas.point.controller.producercontroller.request.ProducerPostRequest;
import br.dev.dantas.point.controller.producercontroller.request.ProducerPutRequest;
import br.dev.dantas.point.controller.producercontroller.response.ProducerGetResponse;
import br.dev.dantas.point.controller.producercontroller.response.ProducerPostResponse;
import br.dev.dantas.point.domain.mappers.ProducerMapper;
import br.dev.dantas.point.service.ProducerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {V1_PATH_DEFAULT})
@Log4j2
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class ProducerController implements IProducerController {

    private final ProducerService producerService;
    private final ProducerMapper mapper;

    @GetMapping
    @Override
    public ResponseEntity<List<ProducerGetResponse>> listAllProducers(@RequestParam(required = false) String name) {
        log.info("Request received to list all anime's, param name '{}' ", name);

        var producers = producerService.findAll(name);
        var producerGetResponses = mapper.toProducerGetResponseList(producers);

        return ResponseEntity.ok(producerGetResponses);
    }

    @GetMapping("{id}")
    @Override
    public ResponseEntity<ProducerGetResponse> findProducerById(@PathVariable @Valid Long id) {
        log.info("Request received find producer by id '{}' ", id);

        var producer = producerService.findById(id);
        var animeGetResponse = mapper.toProducerGetResponse(producer);

        return ResponseEntity.ok(animeGetResponse);
    }

    @PostMapping
    @Override
    public ResponseEntity<ProducerPostResponse> saveProducer(@RequestBody @Valid ProducerPostRequest request) {
        log.info("Request create produce post method '{}' ", request);

        var producer = mapper.toProducer(request);
        producer = producerService.save(producer);
        var response = mapper.toProducerPostResponse(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    @Override
    public ResponseEntity<Void> deleteProducerById(@PathVariable @Valid Long id) {
        log.info("Request received to delete the producer by id'{}' ", id);

        producerService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Override
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> updateProducer(@RequestBody @Valid ProducerPutRequest request) {
        log.info("Request received to update the producer '{}' ", request);

        var producerToUpdate = mapper.toProducer(request);
        producerService.update(producerToUpdate);

        return ResponseEntity.noContent().build();
    }
}
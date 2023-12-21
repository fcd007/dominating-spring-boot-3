package br.dev.dantas.point.controller.producercontroller;


import br.dev.dantas.point.mappers.ProducerMapper;
import br.dev.dantas.point.request.ProducerPostRequest;
import br.dev.dantas.point.request.ProducerPutRequest;
import br.dev.dantas.point.response.ProducerGetResponse;
import br.dev.dantas.point.response.ProducerPostResponse;
import br.dev.dantas.point.service.ProducerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {IProducerController.V1_PATH_DEFAULT, IProducerController.V1_PATH_OTHER})
@Log4j2
public class ProducerController {
    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    private ProducerService producerService;

    public ProducerController() {
        this.producerService = new ProducerService();
    }

    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> list(@RequestParam(required = false) String name) {
        log.info("Request received to list all anime's, param name '{}'", name);

        var producers = producerService.listAll(name);
        var producerGetResponses = MAPPER.toProducerGetResponseList(producers);

        return ResponseEntity.ok(producerGetResponses);
    }

    @PostMapping
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest request) {
        log.info("Request create produce post method '{}'", request);

        var producer = MAPPER.toProducer(request);
        producer = producerService.save(producer);
        var response = MAPPER.toProducerPostResponse(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Request received to delete the producer by id'{}'", id);

        producerService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        log.info("Request received to update the producer '{}'", request);

        var producerToUpdate = MAPPER.toProducer(request);
        producerService.update(producerToUpdate);

        return ResponseEntity.noContent().build();
    }
}
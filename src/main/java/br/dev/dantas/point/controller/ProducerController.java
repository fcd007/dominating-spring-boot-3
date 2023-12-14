package br.dev.dantas.point.controller;


import br.dev.dantas.point.domain.Producer;
import br.dev.dantas.point.mappers.ProducerMapper;
import br.dev.dantas.point.request.ProducerPostRequest;
import br.dev.dantas.point.response.ProducerGetResponse;
import br.dev.dantas.point.response.ProducerPostResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = {"api/v1/producers", "api/v1/producers/"})
@Log4j2
public class ProducerController {
    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> list(@RequestParam(required = false) String name) {
        log.info("Request received to list all anime's, param name '{}'", name);
        var producers = Producer.getProducers();
        var producerGetResponses = MAPPER.toProducerGetResponseList(producers);

        if (name == null) {
            return ResponseEntity.ok(producerGetResponses);
        }

        producerGetResponses = producerGetResponses
                .stream()
                .filter(producer -> producer.getName().equalsIgnoreCase(name))
                .toList();
        return ResponseEntity.ok(producerGetResponses);
    }

    @PostMapping
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest request) {
        log.info("Request create produce post method '{}'", request);

        var mapper = ProducerMapper.INSTANCE;
        var producer = mapper.toProducer(request);
        var response = mapper.toProducerPostResponse(producer);

        Producer.getProducers().add(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var producerFound = Producer.getProducers()
                .stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found to be deleted"));
        Producer.getProducers().remove(producerFound);
        return ResponseEntity.noContent().build();
    }
}
package br.dev.dantas.point.controller;


import br.dev.dantas.point.domain.Producer;
import br.dev.dantas.point.mappers.ProducerMapper;
import br.dev.dantas.point.request.ProducerPostRequest;
import br.dev.dantas.point.response.ProducerPostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"api/v1/producers", "api/v1/producers/"})
public class ProducerController {

    @PostMapping
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest request) {
        var mapper = ProducerMapper.INSTANCE;
        var producer = mapper.toProducer(request);
        var response = mapper.toProducerPostResponse(producer);

        Producer.getProducers().add(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
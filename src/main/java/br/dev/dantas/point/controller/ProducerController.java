package br.dev.dantas.point.controller;


import br.dev.dantas.point.domain.Producer;
import br.dev.dantas.point.mappers.ProducerMapper;
import br.dev.dantas.point.request.ProducerPostRequest;
import br.dev.dantas.point.response.ProducerPostResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"api/v1/producers", "api/v1/producers/"})
@Log4j2
public class ProducerController {

    @PostMapping
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest request) {
        log.info("Request create produce post method '{}'", request);

        var mapper = ProducerMapper.INSTANCE;
        var producer = mapper.toProducer(request);
        var response = mapper.toProducerPostResponse(producer);

        Producer.getProducers().add(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
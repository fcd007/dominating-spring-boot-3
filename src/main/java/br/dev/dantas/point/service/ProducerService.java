package br.dev.dantas.point.service;

import br.dev.dantas.point.domain.Producer;
import br.dev.dantas.point.repository.ProducerHardCodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public class ProducerService {

    private ProducerHardCodeRepository producerHardCodeRepository;

    public ProducerService() {
        this.producerHardCodeRepository = new ProducerHardCodeRepository();
    }

    public List<Producer> listAll(String name) {
        return producerHardCodeRepository.findByName(name);
    }

    public Producer save(Producer producer) {
        return producerHardCodeRepository.save(producer);
    }

    public Optional<Producer> findById(Long id) {
        return producerHardCodeRepository.findById(id);
    }

    public void delete(Long id) {
        var producer = findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found to be deleted"));
        producerHardCodeRepository.delete(producer);
    }

    public void update(Producer producerToUpdate) {
        var producer = findById(producerToUpdate.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found to be updated"));
        producerToUpdate.setCreatedAt(producer.getCreatedAt());
        producerHardCodeRepository.update(producerToUpdate);
    }
}

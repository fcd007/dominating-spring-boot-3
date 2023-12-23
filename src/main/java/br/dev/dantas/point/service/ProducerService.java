package br.dev.dantas.point.service;

import br.dev.dantas.point.domain.Producer;
import br.dev.dantas.point.repository.ProducerHardCodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
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

    public Producer findById(Long id) {
        return producerHardCodeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));
    }

    public void delete(Long id) {
        var producer = findById(id);
        producerHardCodeRepository.delete(producer);
    }

    public void update(Producer producerToUpdate) {
        findById(producerToUpdate.getId());
        producerHardCodeRepository.update(producerToUpdate);
    }
}

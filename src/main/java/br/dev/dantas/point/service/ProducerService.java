package br.dev.dantas.point.service;

import br.dev.dantas.point.domain.entity.Producer;
import br.dev.dantas.point.repository.ProducerRepository;
import exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerRepository repository;

    public List<Producer> findAll(String name) {
        return findByName(name);
    }

    public List<Producer> findByName(String name) {
        return name != null ? repository.findByName(name) : repository.findAll();
    }

    public Producer save(Producer producer) {
        return repository.save(producer);
    }

    public Producer findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Producer not found"));
    }

    public void delete(Long id) {
        var producer = findById(id);
        repository.delete(producer);
    }

    public void update(Producer producerToUpdate) {
        assertProducerExists(producerToUpdate);
        repository.save(producerToUpdate);
    }

    private void assertProducerExists(Producer producerToUpdate) {
        findById(producerToUpdate.getId());
    }
}

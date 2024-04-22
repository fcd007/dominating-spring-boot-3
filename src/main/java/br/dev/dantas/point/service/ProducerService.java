package br.dev.dantas.point.service;

import br.dev.dantas.point.domain.entity.Producer;
import br.dev.dantas.point.repository.ProducerHardCodeRepository;
import exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerHardCodeRepository repository;

    public List<Producer> listAll(String name) {
        return repository.findByName(name);
    }

    public Producer save(Producer producer) {
        return repository.save(producer);
    }

    public Optional<Producer> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        var producer = findById(id).orElseThrow(() -> new NotFoundException("Producer not found to be delete"));
        repository.delete(producer);
    }

    public void update(Producer producerToUpdate) {
        findById(producerToUpdate.getId()).orElseThrow(() -> new NotFoundException("Producer not found to be update"));
        repository.update(producerToUpdate);
    }
}

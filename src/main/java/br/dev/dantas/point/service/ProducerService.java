package br.dev.dantas.point.service;

import br.dev.dantas.point.domain.entity.Producer;
import br.dev.dantas.point.repository.ProducerRepository;
import exception.NotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

  public Optional<Producer> findById(Long id) {
    return repository.findById(id);
  }

  public void delete(Long id) {
    var producer = findById(id).orElseThrow(() -> new NotFoundException("Producer not found"));
    repository.delete(producer);
  }

  public void update(Producer producerToUpdate) {
    findById(producerToUpdate.getId())
        .ifPresentOrElse(p -> repository.save(producerToUpdate), () -> {
          throw new NotFoundException("Producer not found to ne updated");
        });
  }

}

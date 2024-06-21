package br.dev.dantas.point.repository;

import br.dev.dantas.point.domain.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
    List<Producer> findByName(String name);
}

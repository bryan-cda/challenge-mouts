package br.com.ambev.engine.repository;

import br.com.ambev.engine.entity.Fulfillment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Fulfillment, Long> {
    Fulfillment findByCode(UUID code);
}

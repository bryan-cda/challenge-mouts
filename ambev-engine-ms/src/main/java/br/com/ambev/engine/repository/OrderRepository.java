package br.com.ambev.engine.repository;

import br.com.ambev.engine.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByCode(UUID code);
}
